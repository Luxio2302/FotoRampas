package com.example.rampasimage.ui.slideshow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rampasimage.R;
import com.example.rampasimage.ui.home.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adaptermail extends RecyclerView.Adapter<adaptermail.ViewHolder>{

    Usuario usuario;
    private final Context context;

    List<Usuario> lista;

    public adaptermail(List<Usuario> lista, Context context) {
        this.context = context;
        this.lista = lista;
    }


    @NonNull
    @Override
    public adaptermail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_mail,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull adaptermail.ViewHolder holder, int position) {


        Glide.with(holder.imageView.getContext()).load("http://ip/upload_file/RestApi/upload/" + lista.get(position).getImagen()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;

        Button add;

        TextView txcou;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imagemail);
            txcou=itemView.findViewById(R.id.size);
            add=itemView.findViewById(R.id.addImg);
        }
    }
}
