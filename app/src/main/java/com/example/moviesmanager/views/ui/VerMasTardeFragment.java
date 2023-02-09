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
import com.example.moviesmanager.databinding.FragmentVerMasTardeBinding;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.viewmodels.VerMasTardeViewModel;

import java.util.ArrayList;
import java.util.List;

public class VerMasTardeFragment extends Fragment {

    private FragmentVerMasTardeBinding binding;
    private Spinner spinnerVerMasTarde;
    private RecyclerView recyclerViewVerMasTarde;
    private PeliculaAdapter peliculaAdapterVerMasTarde;
    private LinearLayoutManager layoutManagerVerMasTarde;

    private VerMasTardeViewModel verMasTardeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        verMasTardeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VerMasTardeViewModel.class);

        binding = FragmentVerMasTardeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewVerMasTarde = binding.recyclerViewVerMasTarde;
        spinnerVerMasTarde = binding.spinnerVerMasTardeOrden;

        ConfigurarSpinner();
        ConfigurarRecyclerView();
        ObservarCambios();
        verMasTardeViewModel.getPeliculasVerMasTarde();

        return root;
    }

    private void ConfigurarRecyclerView() {
        peliculaAdapterVerMasTarde= new PeliculaAdapter(getContext().getApplicationContext());
        recyclerViewVerMasTarde.setHasFixedSize(true);
        layoutManagerVerMasTarde = new LinearLayoutManager(getContext());
        layoutManagerVerMasTarde.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewVerMasTarde.setLayoutManager(layoutManagerVerMasTarde);
        recyclerViewVerMasTarde.setAdapter(peliculaAdapterVerMasTarde);
    }

    private void ObservarCambios() {
        verMasTardeViewModel.getVerMasTarde().observe((LifecycleOwner) getContext(), new Observer<List<VerMasTarde>>() {
            @Override
            public void onChanged(List<VerMasTarde> verMasTardes) {
                //Observar cambios en la db
                if(verMasTardes!=null){
                    List<Integer> idsPeliculas = new ArrayList<>();
                    for(VerMasTarde verMasTarde : verMasTardes){
                        idsPeliculas.add(verMasTarde.getIdPelicula());
                    }
                    obtenerPeliculasVerMasTarde(idsPeliculas);
                }
            }
        });
        verMasTardeViewModel.getPeliculasVerMasTarde().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                if(peliculas != null){
                    recyclerViewVerMasTarde.setAdapter(new PeliculaAdapter(peliculas,getContext()));
                }
            }
        });

    }

    private void obtenerPeliculasVerMasTarde(List<Integer> idsPeliculas) {
        verMasTardeViewModel.obtenerPeliculasVerMasTarde(idsPeliculas);
    }

    private void ConfigurarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVerMasTarde.setAdapter(adapter);
        spinnerVerMasTarde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                verMasTardeViewModel.ordenarPeliculasVerMasTarde(i);
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