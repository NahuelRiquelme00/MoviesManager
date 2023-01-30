package com.example.moviesmanager.ui.favoritas;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.adapter.PeliculaAdapterInicio;
import com.example.moviesmanager.database.AppDataBase;
import com.example.moviesmanager.databinding.FragmentFavoritasBinding;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.ConsultarTmdbApi;
import com.example.moviesmanager.viewmodels.FavoritasViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FavoritasFragment extends Fragment {

    private FragmentFavoritasBinding binding;
    private Spinner spinnerFavs;
    private RecyclerView recyclerViewFavoritas;
    private PeliculaAdapter peliculaAdapterFavoritas;
    private LinearLayoutManager layoutManagerFavoritas;
    private List<Favorita> listaFavoritas;
    private List<Pelicula> listaPeliculasCargadas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritasViewModel favoritasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoritasViewModel.class);

        binding = FragmentFavoritasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerFavs = binding.spinnerFavsOrden;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFavs.setAdapter(adapter);

        spinnerFavs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 1:
                            listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getTitulo)); //Ascendente
                            break;
                        case 2:
                            listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getTitulo).reversed()); //Descendente
                            break;
                        case 3:
                            listaPeliculasCargadas.sort(Comparator.comparing((Pelicula::getDuracionInteger))); //Menor duracion
                            break;
                        case 4:
                            listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getDuracionInteger).reversed()); //Mayor duracion
                            break;
                        case 5:
                            listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getFechaDeEstreno).reversed()); //Mas reciente
                            break;
                        case 6:
                            listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getFechaDeEstreno)); //Mas antigua
                            break;
                        default:
                            break;
                    }

                final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Cargar Favoritas
                        peliculaAdapterFavoritas = new PeliculaAdapter(listaPeliculasCargadas, getContext());
                        recyclerViewFavoritas = binding.recyclerViewFavs;
                        recyclerViewFavoritas.setHasFixedSize(true);
                        layoutManagerFavoritas = new LinearLayoutManager(getContext());
                        recyclerViewFavoritas.setLayoutManager(layoutManagerFavoritas);
                        recyclerViewFavoritas.setAdapter(peliculaAdapterFavoritas);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            AppDataBase db = AppDataBase.getInstance(getContext().getApplicationContext());
            listaFavoritas = db.daoFavorita().obtenerPeliculas();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error al consultar base de datos" + ex, Toast.LENGTH_LONG).show();
        }

        cargarFavoritas();
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void cargarFavoritas(){
        if(listaFavoritas != null) {
            List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

            for (Favorita f : listaFavoritas) {
                ConsultarTmdbApi consulta = new ConsultarTmdbApi();
                listaPeliculas.add(consulta.obtenerDetalles(f.getIdPelicula()));
            }

            listaPeliculasCargadas = listaPeliculas;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Cargar Favoritas
                    peliculaAdapterFavoritas = new PeliculaAdapter(listaPeliculas, getContext());
                    recyclerViewFavoritas = binding.recyclerViewFavs;
                    recyclerViewFavoritas.setHasFixedSize(true);
                    layoutManagerFavoritas = new LinearLayoutManager(getContext());
                    recyclerViewFavoritas.setLayoutManager(layoutManagerFavoritas);
                    recyclerViewFavoritas.setAdapter(peliculaAdapterFavoritas);
                }
            },1500);
        }
    }
}