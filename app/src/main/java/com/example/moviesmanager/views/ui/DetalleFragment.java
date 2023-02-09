package com.example.moviesmanager.views.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.moviesmanager.R;
import com.example.moviesmanager.database.ConsultarDB;
import com.example.moviesmanager.databinding.FragmentDetalleBinding;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.Valoracion;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.viewmodels.DetalleViewModel;

public class DetalleFragment extends Fragment {

    private FragmentDetalleBinding binding;
    private TextView mTitulo;
    private TextView mDirector;
    private TextView mGenero;
    private TextView mDuracion;
    private TextView mFechaEstreno;
    private ImageView mPoster;
    private Integer id_pelicula;
    public ImageButton mFavorita;
    private Boolean estadoBotonFav;
    private ImageButton mYaVista;
    private Boolean estadoBotonYaVista;
    private ImageButton mVerMasTarde;
    private Boolean estadoBotonVerMasTarde;
    private ImageButton mResena;
    private RatingBar mRatingBar;
    private ConsultarDB db;

    private DetalleViewModel detalleViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //ViewModel
        detalleViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(DetalleViewModel.class);

        //Binding
        binding = FragmentDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mTitulo = binding.titleTextViewDetalle;
        mFechaEstreno = binding.fechaTextViewDetalle;
        mPoster = binding.posterImageViewDetalle;
        mDirector = binding.directorTextViewDetalle;
        mDuracion = binding.duracionTextViewDetalle;
        mGenero = binding.generoTextViewDetalle;
        mFavorita = binding.imageButtonFav;
        estadoBotonFav = false;
        mYaVista = binding.imageButtonYaVista;
        estadoBotonYaVista = false;
        mVerMasTarde = binding.imageButtonVerMasTarde;
        estadoBotonVerMasTarde = false;
        mResena = binding.imageButtonResena;
        mRatingBar = binding.ratingBarDetalle;
        db = ConsultarDB.getInstance(getContext().getApplicationContext());

        //Obtengo el id de la pelicula
        id_pelicula = getArguments().getInt("id_pelicula");
        obtenerDetalles(id_pelicula);

        //Observer
        ObservarCambios();

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
                detalleViewModel.obtenerFavoritas(getContext());
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
                detalleViewModel.obtenerYaVistas(getContext());
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
                detalleViewModel.obtenerVerMasTarde(getContext());
            }
        });

        //Para el fragmento resena se pasan el nombre de la pelicula y el path al poster de la misma
        mResena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Crear reseña ", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("id_pelicula",id_pelicula);
                Navigation.findNavController(view).navigate(R.id.action_nav_detalle_to_reviewFragment, bundle);

            }
        });

        return root;
    }

    private void ObservarCambios() {
        detalleViewModel.getDetalles().observe((LifecycleOwner) getContext(), new Observer<Pelicula>() {
            @Override
            public void onChanged(Pelicula pelicula) {
                //Observar por cambios en la pelicula a ver detalles
                if(pelicula != null){
                    //Modifico los datos a mostrar
                    mTitulo.setText(pelicula.getTitle());
                    if(getContext()!= null){
                        Glide.with(getContext()).load(pelicula.getPoster_path()).into(mPoster);
                    }
                    mDirector.setText(pelicula.getDirector());
                    mGenero.setText(pelicula.getGenero());
                    mDuracion.setText(pelicula.getDuracion() + " min");
                    mFechaEstreno.setText(pelicula.getRelease_date());

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
                    if(db.daoReseña().existsById(id_pelicula)){
                        mResena.setImageResource(R.drawable.ic_baseline_message_outline_24_);
                    }else{
                        mResena.setImageResource(R.drawable.ic_baseline_message_24);
                    }
                }
            }
        });
    }

    private void obtenerDetalles(Integer id_pelicula) {
        detalleViewModel.obtenerDetalles(id_pelicula);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
