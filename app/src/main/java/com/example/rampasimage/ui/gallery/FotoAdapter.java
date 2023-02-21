package com.example.rampasimage.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rampasimage.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder>{

    List<Uri> lista;
    private final Context context;

    public FotoAdapter(List<Uri> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_foto,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = new File(lista.get(position).getPath());
        Picasso.get().load(file).into(holder.imageView);


        holder.txcou.setText(lista.size() + "/13");

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
            imageView=itemView.findViewById(R.id.image);
            txcou=itemView.findViewById(R.id.size);
            add=itemView.findViewById(R.id.addImg);
        }
    }
}
