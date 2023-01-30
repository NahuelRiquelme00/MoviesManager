package com.example.moviesmanager.ui.detalle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.R;
import com.example.moviesmanager.database.AppDataBase;
import com.example.moviesmanager.databinding.FragmentDetalleBinding;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.Valoracion;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.YaVista;
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
    private Boolean estadoBotonFav;
    private ImageButton mYaVista;
    private Boolean estadoBotonYaVista;
    private ImageButton mVerMasTarde;
    private Boolean estadoBotonVerMasTarde;
    private ImageButton mResena;

    private RatingBar mRatingBar;

    private AppDataBase db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            db = AppDataBase.getInstance(getContext().getApplicationContext());
        }catch (Exception ex){
            Toast.makeText(getContext(), "Error al consultar db " + ex, Toast.LENGTH_SHORT).show();
        }

        //uso binding para enlazar las variables
        mTitulo = binding.titleTextViewDetalle;
        mFechaEstreno = binding.fechaTextViewDetalle;
        mPoster = binding.posterImageViewDetalle;
        mDirector = binding.directorTextViewDetalle;
        mDuracion = binding.duracionTextViewDetalle;
        mGenero = binding.generoTextViewDetalle;
        //binding de los botones
        mFavorita = binding.imageButtonFav;
        estadoBotonFav = false;
        mYaVista = binding.imageButtonYaVista;
        estadoBotonYaVista = false;
        mVerMasTarde = binding.imageButtonVerMasTarde;
        estadoBotonVerMasTarde = false;
        mResena = binding.imageButtonResena;
        mRatingBar = binding.ratingBarDetalle;

        //Obtengo el id de la pelicula
        id_pelicula = getArguments().getInt("id_pelicula");

        //Consulto a la api los detalles de la pelicula
        ConsultarTmdbApi consulta = new ConsultarTmdbApi();
        try {
            pelicula = consulta.obtenerDetalles(id_pelicula);
        }catch (Exception ex){
            Toast.makeText(getContext(), "Error al consultar detalles " + ex, Toast.LENGTH_SHORT).show();
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

                Float valoracion = db.daoValoracion().obtenerValoracion(id_pelicula);
                if(valoracion!=null){
                    mRatingBar.setRating(valoracion);
                }
                if(db.daoFavorita().existsById(id_pelicula)){
                    mFavorita.setImageResource(R.drawable.ic_baseline_favorite_24);
                    estadoBotonFav = true;
                }
                if(db.daoYaVista().existsById(id_pelicula)){
                    mYaVista.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    estadoBotonYaVista = true;
                }
                if(db.daoVerMasTarde().existsById(id_pelicula)){
                    mVerMasTarde.setImageResource(R.drawable.ic_baseline_watch_later_24);
                    estadoBotonVerMasTarde = true;
                }
            }
        }, 1000);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float val, boolean b) {
                if(b){ //Si el valor lo cambio el usuario
                    //Guardar o actualizar puntuación
                    try {
                        db.daoValoracion().insertarValoracion(new Valoracion(id_pelicula,val));
                        Toast.makeText(getContext(), "Valoracion guardada ", Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al cargar valoracion " + ex, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estadoBotonFav){ //Si no esta en favoritas la agrego
                    try {
                        db.daoFavorita().insertarPelicula(new Favorita(id_pelicula));
                        Toast.makeText(getContext(), "Agregada a favoritas ", Toast.LENGTH_SHORT).show();
                        mFavorita.setImageResource(R.drawable.ic_baseline_favorite_24);
                        estadoBotonFav = true;
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al cargar favorita " + ex, Toast.LENGTH_SHORT).show();
                    }
                } else { //Si ya estaba en favoritas la elimino
                    try {
                        db.daoFavorita().eliminarPelicula(new Favorita(id_pelicula));
                        Toast.makeText(getContext(), "Eliminada de favoritas ", Toast.LENGTH_SHORT).show();
                        mFavorita.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        estadoBotonFav = false;
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error eliminar de favorita " + ex, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        mYaVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estadoBotonYaVista){
                    try {
                        db.daoYaVista().insertarPelicula(new YaVista(id_pelicula));
                        Toast.makeText(getContext(), "Agregada a ya vista ", Toast.LENGTH_SHORT).show();
                        mYaVista.setImageResource(R.drawable.ic_baseline_check_circle_24);
                        estadoBotonYaVista = true;
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al guardar en ya vista " + ex, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        db.daoYaVista().eliminarPelicula(new YaVista(id_pelicula));
                        Toast.makeText(getContext(), "Eliminada de ya vista ", Toast.LENGTH_SHORT).show();
                        mYaVista.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                        estadoBotonYaVista = false;
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al eliminar de ya vista " + ex, Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        mVerMasTarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estadoBotonVerMasTarde){
                    try {
                        db.daoVerMasTarde().insertarPelicula(new VerMasTarde(id_pelicula));
                        Toast.makeText(getContext(), "Agregada a ver mas tarde ", Toast.LENGTH_SHORT).show();
                        mVerMasTarde.setImageResource(R.drawable.ic_baseline_watch_later_24);
                        estadoBotonVerMasTarde = true;
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al guardar en ver mas tarde " + ex, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        db.daoVerMasTarde().eliminarPelicula(new VerMasTarde(id_pelicula));
                        Toast.makeText(getContext(), "Eliminada de ver mas tarde ", Toast.LENGTH_SHORT).show();
                        mVerMasTarde.setImageResource(R.drawable.ic_outline_watch_later_24);
                        estadoBotonVerMasTarde = false;
                    }catch (Exception ex){
                        Toast.makeText(getContext(), "Error al eliminar de ver mas tarde " + ex, Toast.LENGTH_SHORT).show();
                    }
                }

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
