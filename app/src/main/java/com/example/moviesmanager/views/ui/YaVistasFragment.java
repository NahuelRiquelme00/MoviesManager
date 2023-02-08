package com.example.moviesmanager.views.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.databinding.FragmentYaVistasBinding;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.viewmodels.YaVistasViewModel;

import java.util.ArrayList;
import java.util.List;

public class YaVistasFragment extends Fragment {

    private FragmentYaVistasBinding binding;
    private Spinner spinnerYaVistas;
    private RecyclerView recyclerViewYaVistas;
    private PeliculaAdapter peliculaAdapterYaVistas;
    private LinearLayoutManager layoutManagerYaVistas;

    private YaVistasViewModel yaVistasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       yaVistasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(YaVistasViewModel.class);

        binding = FragmentYaVistasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewYaVistas = binding.recyclerViewYaVistas;
        spinnerYaVistas = binding.spinnerYaVistasOrden;

        ConfigurarSpinner();
        ConfigurarReclycerView();
        ObservarCambios();

        yaVistasViewModel.getPeliculasYaVistas();

        return root;
    }

    private void ObservarCambios() {
        yaVistasViewModel.getYaVistas().observe((LifecycleOwner) getContext(), new Observer<List<YaVista>>() {
            @Override
            public void onChanged(List<YaVista> yaVistas) {
                //Observar cambios en la db
                if(yaVistas!=null){
                    List<Integer> idsPeliculas = new ArrayList<>();
                    for(YaVista yaVista : yaVistas){
                        idsPeliculas.add(yaVista.getIdPelicula());
                    }
                    obtenerPeliculasYaVistas(idsPeliculas);
                }
            }
        });
        yaVistasViewModel.getPeliculasYaVistas().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                //Observar cambios en la lista de peliculas vistas
                if(peliculas != null){
                    recyclerViewYaVistas.setAdapter(new PeliculaAdapter(peliculas, getContext()));
                }
            }
        });
    }

    private void obtenerPeliculasYaVistas(List<Integer> idsPeliculas) {
        yaVistasViewModel.obtenerPeliculasYaVistas(idsPeliculas);
    }

    private void ConfigurarReclycerView() {
        peliculaAdapterYaVistas= new PeliculaAdapter(getContext().getApplicationContext());
        recyclerViewYaVistas.setHasFixedSize(true);
        layoutManagerYaVistas = new LinearLayoutManager(getContext());
        layoutManagerYaVistas.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewYaVistas.setLayoutManager(layoutManagerYaVistas);
        recyclerViewYaVistas.setAdapter(peliculaAdapterYaVistas);
    }

    private void ConfigurarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYaVistas.setAdapter(adapter);
        spinnerYaVistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yaVistasViewModel.ordenarPeliculasYaVistas(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}