package com.example.moviesmanager.views.ui;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.databinding.FragmentRecomendacionesBinding;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.Genre;
import com.example.moviesmanager.viewmodels.RecomendacionesViewModel;

import java.util.List;

public class RecomendacionesFragment extends Fragment {

    private FragmentRecomendacionesBinding binding;
    private Spinner spinnerGeneros;
    private EditText mYear;
    private SeekBar mValoracion;
    private Button mButtonBuscar;
    Integer year = null;
    String id_genero = null;
    Integer valoracion = null;
    private RecyclerView recyclerView;
    private PeliculaAdapter peliculaAdapter;

    private Boolean recomendacionRealizada = false;

    private RecomendacionesViewModel recomendacionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        recomendacionesViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RecomendacionesViewModel.class);

        binding = FragmentRecomendacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mYear = binding.editTextFechaEstreno;
        mButtonBuscar = binding.buttonRecomsBuscar;
        mValoracion = binding.seekBarValoracion;
        recyclerView = binding.recyclerViewRecoms;
        spinnerGeneros = binding.spinnerRecomsGenero;

        observarCambios();

        configurarRecyclerView();

        configurarSpinner();

        mButtonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(mYear.getText())){
                    year = Integer.parseInt(mYear.getText().toString());
                }else year = null;

                if(!spinnerGeneros.getSelectedItem().toString().isEmpty()){
                    String genero = spinnerGeneros.getSelectedItem().toString();
                    id_genero = String.valueOf(Genre.getIdFromGenre(genero));
                }else id_genero = null;

                if(mValoracion.getProgress()!=0){
                    valoracion = mValoracion.getProgress();
                }else valoracion = null;

                Toast.makeText(getContext(), "Cargando recomendaciones ", Toast.LENGTH_SHORT).show();
                buscarPeliculasRecomendadasTMDB(year,valoracion,id_genero);
                recomendacionRealizada = true;
            }
        });

        return root;
    }

    private void observarCambios(){
        recomendacionesViewModel.getPeliculasRecomendadas().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                //Observar por cambios en la lista
                if(peliculas != null){
                    recyclerView.setAdapter(new PeliculaAdapter(peliculas, getContext()));
                }
                if(peliculas.isEmpty()){
                    Toast.makeText(getContext(), "No se encontraron resultados ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buscarPeliculasRecomendadasTMDB(Integer year, Integer valoracion, String id_genero){
        recomendacionesViewModel.buscarPeliculasRecomendadasTMDB(year,valoracion,id_genero);
    }

    private void configurarRecyclerView(){
        peliculaAdapter = new PeliculaAdapter(getContext());
        if(!recomendacionRealizada) recyclerView.setAdapter(peliculaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void configurarSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.generos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGeneros.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}