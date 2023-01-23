package com.example.moviesmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.R;
import com.example.moviesmanager.models.Pelicula;

import java.util.List;

public class PeliculaAdapterInicio extends RecyclerView.Adapter<PeliculaAdapterInicio.ViewHolder> {

    private List<Pelicula> peliculas;
    private Context context;

    public PeliculaAdapterInicio(List<Pelicula> peliculas, Context context) {
        this.peliculas = peliculas;
        this.context = context;
    }

    @NonNull
    @Override
    public PeliculaAdapterInicio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peliculas_inicio,parent,false);
        return new PeliculaAdapterInicio.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaAdapterInicio.ViewHolder holder, int position) {
        Glide.with(context).load(peliculas.get(position).getPosterPath()).error(R.drawable.ic_baseline_no_accounts_24).into(holder.iv_portada);
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_portada;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_portada = itemView.findViewById(R.id.imageViewPoster);
        }
    }

}

