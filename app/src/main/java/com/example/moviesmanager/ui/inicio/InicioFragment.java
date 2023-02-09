package com.example.moviesmanager.ui.inicio;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapterInicio;
import com.example.moviesmanager.database.AppDataBase;
import com.example.moviesmanager.databinding.FragmentInicioBinding;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.network.ConsultarTmdbApi;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;

    private RecyclerView recyclerViewInicioFavoritas;
    private PeliculaAdapterInicio peliculaAdapterInicioFavoritas;
    private LinearLayoutManager layoutManagerInicioFavoritas;
    private RecyclerView recyclerViewInicioVerMasTarde;
    private PeliculaAdapterInicio peliculaAdapterInicioVerMasTarde;
    private LinearLayoutManager layoutManagerInicioVerMasTarde;
    private List<Favorita> listaFavoritas;
    private List<VerMasTarde> listaVerMasTarde;

    private Button mYaVistas;
    private Button mRecomendaciones;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mYaVistas = binding.buttonInicioYaVistas;
        mRecomendaciones = binding.buttonInicioRecomendaciones;

        //Peliculas favoritas y ver mas tarde
        try {
            AppDataBase db = AppDataBase.getInstance(getContext().getApplicationContext());
            listaFavoritas = db.daoFavorita().obtenerPeliculas();
            listaVerMasTarde = db.daoVerMasTarde().obtenerPeliculas();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error al consultar base de datos" + ex, Toast.LENGTH_LONG).show();
        }

        cargarFavoritas();
        cargarVerMasTarde();

        mYaVistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_inicio_to_nav_ya_vistas);
            }
        });

        mRecomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_inicio_to_nav_recomendaciones);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void cargarFavoritas (){
        if(listaFavoritas != null) {

            List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

            for (Favorita f : listaFavoritas) {
                ConsultarTmdbApi consulta = new ConsultarTmdbApi();
                listaPeliculas.add(consulta.obtenerDetalles(f.getIdPelicula()));
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Cargar Favoritas
                    peliculaAdapterInicioFavoritas = new PeliculaAdapterInicio(listaPeliculas, getContext());
                    recyclerViewInicioFavoritas = binding.recyclerViewInicioFavoritas;
                    recyclerViewInicioFavoritas.setHasFixedSize(true);
                    layoutManagerInicioFavoritas = new LinearLayoutManager(getContext());
                    layoutManagerInicioFavoritas.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerViewInicioFavoritas.setLayoutManager(layoutManagerInicioFavoritas);
                    recyclerViewInicioFavoritas.setAdapter(peliculaAdapterInicioFavoritas);
                }
            }, 2000);

        }
    }

    void cargarVerMasTarde(){
        if(listaVerMasTarde != null) {

            List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

            for (VerMasTarde v : listaVerMasTarde) {
                ConsultarTmdbApi consulta = new ConsultarTmdbApi();
                listaPeliculas.add(consulta.obtenerDetalles(v.getIdPelicula()));
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Cargar posters
                    peliculaAdapterInicioVerMasTarde = new PeliculaAdapterInicio(listaPeliculas, getContext());
                    recyclerViewInicioVerMasTarde = binding.recyclerViewInicioVerMasTarde;
                    recyclerViewInicioVerMasTarde.setHasFixedSize(true);
                    layoutManagerInicioVerMasTarde = new LinearLayoutManager(getContext());
                    layoutManagerInicioVerMasTarde.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerViewInicioVerMasTarde.setLayoutManager(layoutManagerInicioVerMasTarde);
                    recyclerViewInicioVerMasTarde.setAdapter(peliculaAdapterInicioVerMasTarde);
                }
            }, 2000);

        }
    }
}