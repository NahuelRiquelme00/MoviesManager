package com.example.moviesmanager.ui.busqueda;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.databinding.FragmentBusquedaBinding;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.ConsultarTmdbApi;
import com.example.moviesmanager.viewmodels.BusquedaViewModel;

import java.util.List;

public class BusquedaFragment extends Fragment {

    private FragmentBusquedaBinding binding;
    private Button btn_buscar;
    private EditText edt_titulo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusquedaViewModel busquedaViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BusquedaViewModel.class);

        binding = FragmentBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_buscar = binding.buttonSearch;
        edt_titulo = binding.editTextMovieTitle;

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edt_titulo.getText().toString().isEmpty()) {

                    ConsultarTmdbApi rg = new ConsultarTmdbApi();
                    try {
                        List<Pelicula> peliculas = rg.respuesta(edt_titulo.getText().toString());
                        Toast.makeText(getContext(), "Procesando ...", Toast.LENGTH_SHORT).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Mostrar el resultado de la busqueda
                                // El resultado son peliculas
                                PeliculaAdapter peliculaAdapter = new PeliculaAdapter(peliculas, getContext());
                                RecyclerView recyclerView = binding.peliculasRecyclerView;
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(peliculaAdapter);

                            }
                        }, 5000);
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "Error: " + ex, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Debe ingresar un titulo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //final TextView textView = binding.textBusqueda;
        //busquedaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}