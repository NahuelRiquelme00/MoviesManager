package com.example.moviesmanager.ui.detalle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.databinding.FragmentDetalleBinding;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.ConsultarTmdbApi;

public class DetalleFragment extends Fragment {

    private FragmentDetalleBinding binding;
    private TextView mTitulo;
    private TextView mDirector;
    private TextView mGenero;
    private TextView mDuracion;
    private TextView mFechaEstreno;
    private ImageView mPoster;
    private Integer id_pelicula;
    private Pelicula pelicula;

    private ImageButton mFavorita;
    private ImageButton mYaVista;
    private ImageButton mVerMasTarde;
    private ImageButton mResena;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //uso binding para enlazar las variables
        TextView mTitulo = binding.titleTextViewDetalle;
        TextView mFechaEstreno = binding.fechaTextViewDetalle;
        ImageView mPoster = binding.posterImageViewDetalle;
        TextView mDirector = binding.directorTextViewDetalle;
        TextView mDuracion = binding.duracionTextViewDetalle;
        TextView mGenero = binding.generoTextViewDetalle;

        //Obtengo el id de la pelicula
        id_pelicula = getArguments().getInt("id_pelicula");

        //Consulto a la api los detalles de la pelicula
        ConsultarTmdbApi consulta = new ConsultarTmdbApi();
        try {
            pelicula = consulta.obtenerDetalles(id_pelicula);
        }catch (Exception ex){
            Toast.makeText(getContext(), "Error al consulta detalles " + ex, Toast.LENGTH_SHORT).show();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Cargo los detalles de la pelicula en el layout
                mTitulo.setText(pelicula.getTitulo());
                Glide.with(getContext()).load(pelicula.getPosterPath()).into(mPoster);
                mDirector.setText(pelicula.getDirector());
                mGenero.setText(pelicula.getGenero());
                mDuracion.setText(pelicula.getDuracion() + " min");
                mFechaEstreno.setText(pelicula.getFechaDeEstreno());
            }
        }, 1000);

        //binding de los botones
        mFavorita = binding.imageButtonFav;
        mYaVista = binding.imageButtonYaVista;
        mVerMasTarde = binding.imageButtonVerMasTarde;
        mResena = binding.imageButtonResena;

        mFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Agregada a favoritas ", Toast.LENGTH_SHORT).show();
            }
        });

        mYaVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Agregada a ya vista ", Toast.LENGTH_SHORT).show();
            }
        });

        mVerMasTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Agregada a ver mas tarde ", Toast.LENGTH_SHORT).show();
            }
        });

        mResena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Crear reseña ", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
