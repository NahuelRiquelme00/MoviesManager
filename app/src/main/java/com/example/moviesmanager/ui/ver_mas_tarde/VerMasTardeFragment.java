package com.example.moviesmanager.ui.ver_mas_tarde;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapter;
import com.example.moviesmanager.database.AppDataBase;
import com.example.moviesmanager.databinding.FragmentVerMasTardeBinding;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.ConsultarTmdbApi;
import com.example.moviesmanager.viewmodels.VerMasTardeViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VerMasTardeFragment extends Fragment {

    private FragmentVerMasTardeBinding binding;

    private Spinner spinnerVerMasTarde;
    private RecyclerView recyclerViewVerMasTarde;
    private PeliculaAdapter peliculaAdapterVerMasTarde;
    private LinearLayoutManager layoutManagerVerMasTarde;
    private List<VerMasTarde> listaVerMasTarde;
    private List<Pelicula> listaPeliculasCargadas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VerMasTardeViewModel ver_mas_tardeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(VerMasTardeViewModel.class);

        binding = FragmentVerMasTardeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerVerMasTarde = binding.spinnerVerMasTardeOrden;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVerMasTarde.setAdapter(adapter);

        spinnerVerMasTarde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getDuracion)); //Menor duracion
                        break;
                    case 4:
                        listaPeliculasCargadas.sort(Comparator.comparing(Pelicula::getDuracion).reversed()); //Mayor duracion
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
                        peliculaAdapterVerMasTarde = new PeliculaAdapter(listaPeliculasCargadas, getContext());
                        recyclerViewVerMasTarde = binding.recyclerViewVerMasTarde;
                        recyclerViewVerMasTarde.setHasFixedSize(true);
                        layoutManagerVerMasTarde = new LinearLayoutManager(getContext());
                        recyclerViewVerMasTarde.setLayoutManager(layoutManagerVerMasTarde);
                        recyclerViewVerMasTarde.setAdapter(peliculaAdapterVerMasTarde);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            AppDataBase db = AppDataBase.getInstance(getContext().getApplicationContext());
            listaVerMasTarde = db.daoVerMasTarde().obtenerPeliculas();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error al consultar base de datos" + ex, Toast.LENGTH_LONG).show();
        }

        cargarVerMasTarde();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void cargarVerMasTarde(){
        if(listaVerMasTarde != null) {
            List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

            for (VerMasTarde f : listaVerMasTarde) {
                ConsultarTmdbApi consulta = new ConsultarTmdbApi();
                listaPeliculas.add(consulta.obtenerDetalles(f.getIdPelicula()));
            }

            listaPeliculasCargadas = listaPeliculas;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Cargar VerMasTarde
                    peliculaAdapterVerMasTarde = new PeliculaAdapter(listaPeliculas, getContext());
                    recyclerViewVerMasTarde = binding.recyclerViewVerMasTarde;
                    recyclerViewVerMasTarde.setHasFixedSize(true);
                    layoutManagerVerMasTarde = new LinearLayoutManager(getContext());
                    recyclerViewVerMasTarde.setLayoutManager(layoutManagerVerMasTarde);
                    recyclerViewVerMasTarde.setAdapter(peliculaAdapterVerMasTarde);
                }
            }, 1000);
        }
    }
}