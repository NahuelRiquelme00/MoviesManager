package com.example.moviesmanager.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.R;
import com.example.moviesmanager.models.Pelicula;

import java.util.ArrayList;
import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private List<Pelicula> peliculas;
    private Context context;

    public PeliculaAdapter(Context context) {
        this.peliculas = new ArrayList<>();
        this.context = context;
    }

    public PeliculaAdapter(List<Pelicula> peliculas , Context context) {
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
        holder.tv_titulo.setText(peliculas.get(position).getTitle());
        holder.tv_anio.setText(peliculas.get(position).getRelease_date());
        Glide.with(context).load(peliculas.get(position).getPoster_path())
                .error(R.drawable.ic_baseline_no_accounts_24).into(holder.iv_portada);

        //Evento para ver si se selecciona una de las peliculas
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_pelicula",peliculas.get(holder.getAdapterPosition()).getId());
                Navigation.findNavController(view).navigate(R.id.action_global_nav_detalle,bundle);
                Toast.makeText(view.getContext(), "Cargando detalles...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(peliculas != null){
            return peliculas.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_portada;
        TextView tv_titulo;
        TextView tv_anio;
        CardView cv_pelicula;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_titulo = itemView.findViewById(R.id.titleTextView);
            tv_anio = itemView.findViewById(R.id.anioTextView);
            iv_portada = itemView.findViewById(R.id.posterImageView);
            cv_pelicula = itemView.findViewById(R.id.cv_pelicula);
        }
    }
}
