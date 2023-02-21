package com.example.rampasimage.ui.gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rampasimage.R;
import com.example.rampasimage.databinding.FragmentGalleryBinding;
import com.example.rampasimage.ui.home.HomeFragment;
import com.example.rampasimage.ui.home.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GalleryFragment extends Fragment {

    FragmentGalleryBinding binding;
    RecyclerView listaFotos;
    FotoAdapter adapter;
    Usuario usuario;
    TextView etTipo,etCaja,etdestino,etemb;
    Button btnSubmit;
    FragmentTransaction fragmentTransaction;
    private List<Uri> fotos =  new ArrayList<>();
    private static final int PICTURE_RESULT = 122 ;
    private ContentValues values;
    private Uri imageUri;
    private Bitmap thumbnail;
    String imageurl;
    public GalleryFragment() {

    }

    public void setDatos (Usuario datos){
        this.usuario = datos;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        btnSubmit = root.findViewById(R.id.addImg);
        listaFotos=binding.listaFotos;
        adapter=new FotoAdapter(fotos, getContext());
        listaFotos.setLayoutManager(new GridLayoutManager(getContext(), 4));
        listaFotos.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            if(!checkExternalStoragePermission()){
            }
        }

        FloatingActionButton btn=binding.fotografiar;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                imageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PICTURE_RESULT);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToServer();
            }
        });

        return root;
    }

    //method to call api to upload files to server
    public void uploadToServer(){

        List<MultipartBody.Part> list = new ArrayList<>();
        for(Uri uri: fotos){
            list.add(prepairFiles("file[]", uri));
        }

        HttpService service = RetrofitBuilder.getClient().create(HttpService.class);

        Call<FileModel> call = service.callMultipleUploadApi(list);

        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {

                FileModel model = response.body();
                Toast.makeText(getContext(), model.getMessage(), Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setDatos(usuario);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, homeFragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri){
        File file = new File( fileUri.getPath());

        RequestBody requestBody = RequestBody.create(MediaType.parse(""), file);

        return  MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private boolean checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer!");
            return true;
        }

        return false;
    }

    public Uri getImageUri(Context context, Bitmap bitmap){
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, usuario.getId_reg(),"");
        return Uri.parse(path);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTipo = view.findViewById(R.id.tvtipo);
        etCaja = view.findViewById(R.id.tvtrailer);
        etdestino = view.findViewById(R.id.tvdest);
        etemb = view.findViewById(R.id.tvemb);


        etTipo.setText("" + usuario.getTipo());
        etCaja.setText(usuario.getCaja());
        etdestino.setText(usuario.getDestino());
        etemb.setText(usuario.getId_reg());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICTURE_RESULT:
                if (requestCode == PICTURE_RESULT)
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                            //Obtiene la ruta donde se encuentra guardada la imagen.
                            imageurl = getRealPathFromURI(getImageUri(getContext(),thumbnail));
                            fotos.add(Uri.parse(imageurl));

                            FotoAdapter adapter = new FotoAdapter(fotos,getContext());
                            listaFotos.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }
    }
}