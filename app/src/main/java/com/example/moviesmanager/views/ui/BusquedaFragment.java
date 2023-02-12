package com.example.moviesmanager.views.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.databinding.FragmentBusquedaBinding;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.viewmodels.BusquedaViewModel;

import java.util.List;

public class BusquedaFragment extends Fragment {

    private FragmentBusquedaBinding binding;
    private Button btn_buscar;
    private EditText edt_titulo;
    private RecyclerView recyclerView;
    private PeliculaAdapter peliculaAdapter;
    private String consulta;

    private BusquedaViewModel busquedaViewModel;

    private Boolean busquedaRealizada = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        busquedaViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BusquedaViewModel.class);

        binding = FragmentBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn_buscar = binding.buttonSearch;
        edt_titulo = binding.editTextMovieTitle;
        recyclerView = binding.peliculasRecyclerView;

        ObservarCambios();

        configurarReclyclerView();

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consulta = edt_titulo.getText().toString().trim();
                if(consulta != null){
                    if(consulta.isEmpty() ){
                        Toast.makeText(getContext(), "Ingrese el nombre de la pelicula ", Toast.LENGTH_SHORT).show();
                    }else{
                        buscarPeliculaTMDB(consulta);
                        busquedaRealizada = true;
                    }
                }
            }
        });
        return root;
    }

    private void ObservarCambios(){
        busquedaViewModel.getPeliculas().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                //Observar por cambios en la lista
                if(peliculas != null){
                    if(peliculas.isEmpty()){
                        Toast.makeText(getContext(), "No se encontraron resultados ", Toast.LENGTH_SHORT).show();
                    }else{
                        recyclerView.setAdapter(new PeliculaAdapter(peliculas, getContext()));
                    }
                }
            }
        });
    }

    //4 - se llama al metodo desde el fragmento
    private void buscarPeliculaTMDB(String consulta){
        busquedaViewModel.buscarPeliculaTMDB(consulta);
    }

    private void configurarReclyclerView(){
        peliculaAdapter = new PeliculaAdapter(getContext());
        if(!busquedaRealizada) recyclerView.setAdapter(peliculaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}