package com.example.moviesmanager.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.R;
import com.example.moviesmanager.models.Pelicula;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> { 
    private List<Pelicula> peliculas;
    private Context context;

    public PeliculaAdapter(List<Pelicula> peliculas, Context context) {
        this.peliculas = peliculas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peliculas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Se cargan los datos en el holder
        holder.tv_titulo.setText(peliculas.get(position).getTitulo());
        holder.tv_anio.setText(peliculas.get(position).getFechaDeEstreno());
        Glide.with(context).load(peliculas.get(position).getPosterPath()).error(R.drawable.ic_baseline_no_accounts_24).into(holder.iv_portada);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_portada;
        TextView tv_titulo;
        TextView tv_anio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_titulo = itemView.findViewById(R.id.titleTextView);
            tv_anio = itemView.findViewById(R.id.anioTextView);
            iv_portada = itemView.findViewById(R.id.posterImageView);
        }
    }
}
