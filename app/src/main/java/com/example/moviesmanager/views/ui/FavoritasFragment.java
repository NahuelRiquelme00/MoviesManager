package com.example.moviesmanager.views.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.databinding.FragmentFavoritasBinding;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.viewmodels.FavoritasViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritasFragment extends Fragment {

    private FragmentFavoritasBinding binding;
    private Spinner spinnerFavs;
    private RecyclerView recyclerViewFavoritas;
    private PeliculaAdapter peliculaAdapterFavoritas;
    private LinearLayoutManager layoutManagerFavoritas;

    private FavoritasViewModel favoritasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        favoritasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoritasViewModel.class);

        binding = FragmentFavoritasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewFavoritas = binding.recyclerViewFavs;
        spinnerFavs = binding.spinnerFavsOrden;

        ConfigurarSpinner();
        ConfigurarRecyclerViewFavs();
        ObservarCambios();
        favoritasViewModel.getPeliculasFavoritas();
        
        return root;
    }

    private void ConfigurarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFavs.setAdapter(adapter);
        spinnerFavs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                favoritasViewModel.ordenarPeliculasFavoritas(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void ConfigurarRecyclerViewFavs() {
        peliculaAdapterFavoritas= new PeliculaAdapter(getContext().getApplicationContext());
        recyclerViewFavoritas.setHasFixedSize(true);
        layoutManagerFavoritas = new LinearLayoutManager(getContext());
        layoutManagerFavoritas.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewFavoritas.setLayoutManager(layoutManagerFavoritas);
        recyclerViewFavoritas.setAdapter(peliculaAdapterFavoritas);
    }

    private void ObservarCambios() {
        favoritasViewModel.getFavoritas().observe((LifecycleOwner) getContext(), new Observer<List<Favorita>>() {
            @Override
            public void onChanged(List<Favorita> favoritas) {
                //Observar cambios en la db
                //Si hay cambios en la db hay cambios en la lista de peliculas favoritas a mostrar
                if(favoritas!=null){
                    List<Integer> idsPeliculas = new ArrayList<>();
                    for(Favorita favorita : favoritas){
                        idsPeliculas.add(favorita.getIdPelicula());
                    }
                    obtenerPeliculasFavoritas(idsPeliculas);
                }
            }
        });
        favoritasViewModel.getPeliculasFavoritas().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                if(peliculas != null){
                    recyclerViewFavoritas.setAdapter(new PeliculaAdapter(peliculas, getContext()));
                }
            }
        });
    }

    private void obtenerPeliculasFavoritas(List<Integer> idsPeliculas) {
        favoritasViewModel.obtenerPeliculasFavoritas(idsPeliculas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
