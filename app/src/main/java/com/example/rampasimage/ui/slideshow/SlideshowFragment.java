package com.example.rampasimage.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rampasimage.R;
import com.example.rampasimage.databinding.FragmentSlideshowBinding;
import com.example.rampasimage.ui.home.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    adaptermail adapter;

    Usuario usuario;

    TextView etTipo,etCaja,etdestino,etemb;
    Button btnSubmit;
    FragmentTransaction fragmentTransaction;

    private String selectedImage;
    private List<Usuario> fotos =  new ArrayList<>();
    public SlideshowFragment() {

    }

    public void setDatos (Usuario datos){
        this.usuario = datos;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSubmit = root.findViewById(R.id.addImg);
        binding.listaMail.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.listaMail.setAdapter(adapter);
        adapter = new adaptermail(fotos, getContext());

        return root;
    }

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

}