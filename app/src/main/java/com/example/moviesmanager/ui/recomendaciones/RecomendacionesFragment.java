package com.example.moviesmanager.ui.recomendaciones;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.databinding.FragmentRecomendacionesBinding;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.ConsultarTmdbApi;
import com.example.moviesmanager.network.Genre;
import com.example.moviesmanager.viewmodels.RecomendacionesViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecomendacionesFragment extends Fragment {

    private FragmentRecomendacionesBinding binding;
    private Spinner spinnerGeneros;
    private EditText mYear;
    private SeekBar mValoracion;
    private Button mButtonBuscar;
    private List<Pelicula> peliculas = new ArrayList<>();
    Integer year = null;
    String id_genero = null;
    Integer valoracion = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecomendacionesViewModel recomendacionesViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RecomendacionesViewModel.class);

        binding = FragmentRecomendacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerGeneros = binding.spinnerRecomsGenero;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.generos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGeneros.setAdapter(adapter);

        mYear = binding.editTextFechaEstreno;
        mButtonBuscar = binding.buttonRecomsBuscar;
        mValoracion = binding.seekBarValoracion;

        mButtonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(mYear.getText())){
                    year = Integer.parseInt(mYear.getText().toString());
                }

                if(!spinnerGeneros.getSelectedItem().toString().isEmpty()){
                    String genero = spinnerGeneros.getSelectedItem().toString();
                    id_genero = String.valueOf(Genre.getIdFromGenre(genero));
                }

                if(mValoracion.getProgress()!=0){
                    valoracion = mValoracion.getProgress();
                }

                ConsultarTmdbApi rg = new ConsultarTmdbApi();
                try{
                    peliculas = rg.obtenerRecomendaciones(year,valoracion,id_genero);
                    Toast.makeText(getContext(), "Buscando recomendaciones...", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(getContext(), "Error: " + ex, Toast.LENGTH_SHORT).show();
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(peliculas.isEmpty()){
                            Toast.makeText(getContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Cargando resultados...", Toast.LENGTH_SHORT).show();
                            //Mostrar el resultado de la busqueda
                            // El resultado son peliculas
                            PeliculaAdapter peliculaAdapter = new PeliculaAdapter(peliculas, getContext());
                            RecyclerView recyclerView = binding.recyclerViewRecoms;
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(peliculaAdapter);
                        }
                    }
                }, 2000);

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