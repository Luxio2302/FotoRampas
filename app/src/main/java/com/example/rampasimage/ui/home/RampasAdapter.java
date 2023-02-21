package com.example.rampasimage.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rampasimage.R;
import com.example.rampasimage.ui.gallery.GalleryFragment;
import com.example.rampasimage.ui.slideshow.SlideshowFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by CHENAO on 6/08/2017.
 */

public class RampasAdapter extends RecyclerView.Adapter<RampasAdapter.UsuariosHolder>{

    List<Usuario> listaUsuario;
    Context context;
    FragmentTransaction fragmentTransaction;

    public RampasAdapter(Context context, List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
        this.context = context;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int i) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.embarques_list,parent,false);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder,  @SuppressLint("RecyclerView") final int i) {
        final Usuario usuario = listaUsuario.get(i);

        holder.txRampa.setText(""+usuario.getRampa());
        holder.txCaja.setText(""+usuario.getCaja());
        holder.txDest.setText(""+usuario.getDestino());
        holder.txSal.setText(""+usuario.getSalida());
        holder.txTipo.setText(""+usuario.getTipo());


        // set events
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                GalleryFragment galleryFragment = new GalleryFragment();
                galleryFragment.setDatos(usuario);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, galleryFragment);
                fragmentTransaction.commit();
            }
        });

        holder.btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SlideshowFragment slideshowFragment = new SlideshowFragment();
                slideshowFragment.setDatos(usuario);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, slideshowFragment);
                fragmentTransaction.commit();
            }
        });


    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{
        //textview
        TextView txRampa,txCaja,txDest,txSal,txTipo;

        //cardview
        CardView cvTarjeta;

        //buttons
        Button btnDetails,btnemail;

        public UsuariosHolder(View itemView) {
            super(itemView);

            txRampa = (TextView) itemView.findViewById(R.id.txtRampa);
            cvTarjeta = itemView.findViewById(R.id.cvTarjeta);
            txCaja= (TextView) itemView.findViewById(R.id.txtCaja);
            txDest= (TextView) itemView.findViewById(R.id.txtdestino);
            txTipo= (TextView) itemView.findViewById(R.id.txtTipo);
            txSal= (TextView) itemView.findViewById(R.id.txtSalida);

            btnDetails= (Button) itemView.findViewById(R.id.btnDetalles);
            btnemail = (Button) itemView.findViewById(R.id.btnEmail);


        }
    }

}
