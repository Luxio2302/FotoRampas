package com.example.rampasimage.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rampasimage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerUsuarios;
    ArrayList<Usuario> listaUsuarios;
    RampasAdapter adaptador;
    Usuario usuario;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_home, container, false);

        recyclerUsuarios= (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager(new GridLayoutManager(this.getContext(), 1));

        listaUsuarios=new ArrayList<>();

        cargarWebService();

        adaptador = new RampasAdapter(getActivity(), listaUsuarios);
        recyclerUsuarios.setAdapter(adaptador);

        return vista;
    }

    public void cargarWebService() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.ip),
                response -> {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("Usuarios");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String idreg = jsonObject1.getString("id_emb");
                            String nr = jsonObject1.getString("rampa");
                            String name = jsonObject1.getString("trailer");
                            String puesto = jsonObject1.getString("plant_code");
                            String dept = jsonObject1.getString("accion");
                            String ven = jsonObject1.getString("type_material");
                            String img = jsonObject1.getString("prog_date");
                            String imag = jsonObject1.getString("name");


                            listaUsuarios.add(new Usuario(idreg,nr, name, puesto, dept, ven, img, imag));

                        }

                        adaptador = new RampasAdapter(getActivity(), listaUsuarios);
                        recyclerUsuarios.setAdapter(adaptador);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace
        );

        requestQueue.add(stringRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setDatos(Usuario datos) {
        this.usuario = datos;
    }
}