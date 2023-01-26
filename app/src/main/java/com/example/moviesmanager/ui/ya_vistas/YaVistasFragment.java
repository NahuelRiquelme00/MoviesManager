package com.example.moviesmanager.ui.ya_vistas;

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
import com.example.moviesmanager.databinding.FragmentYaVistasBinding;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.network.ConsultarTmdbApi;
import com.example.moviesmanager.viewmodels.YaVistasViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class YaVistasFragment extends Fragment {

    private FragmentYaVistasBinding binding;

    private Spinner spinnerYaVistas;
    private RecyclerView recyclerViewYaVistas;
    private PeliculaAdapter peliculaAdapterYaVistas;
    private LinearLayoutManager layoutManagerYaVistas;
    private List<YaVista> listaYaVistas;
    private List<Pelicula> listaPeliculasCargadas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        YaVistasViewModel yaVistasViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(YaVistasViewModel.class);

        binding = FragmentYaVistasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Spinner
        spinnerYaVistas = binding.spinnerYaVistasOrden;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYaVistas.setAdapter(adapter);

        spinnerYaVistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        //Recargar ya vistas ordenadas
                        peliculaAdapterYaVistas = new PeliculaAdapter(listaPeliculasCargadas, getContext());
                        recyclerViewYaVistas = binding.recyclerViewYaVistas;
                        recyclerViewYaVistas.setHasFixedSize(true);
                        layoutManagerYaVistas = new LinearLayoutManager(getContext());
                        recyclerViewYaVistas.setLayoutManager(layoutManagerYaVistas);
                        recyclerViewYaVistas.setAdapter(peliculaAdapterYaVistas);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            AppDataBase db = AppDataBase.getInstance(getContext().getApplicationContext());
            listaYaVistas = db.daoYaVista().obtenerPeliculas();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error al consultar base de datos" + ex, Toast.LENGTH_LONG).show();
        }

        cargarYaVistas();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void cargarYaVistas(){
        if(listaYaVistas != null) {
            List<Pelicula> listaPeliculas = new ArrayList<Pelicula>();

            for (YaVista f : listaYaVistas) {
                ConsultarTmdbApi consulta = new ConsultarTmdbApi();
                listaPeliculas.add(consulta.obtenerDetalles(f.getIdPelicula()));
            }

            listaPeliculasCargadas = listaPeliculas;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Cargar YaVistas
                    peliculaAdapterYaVistas = new PeliculaAdapter(listaPeliculas, getContext());
                    recyclerViewYaVistas = binding.recyclerViewYaVistas;
                    recyclerViewYaVistas.setHasFixedSize(true);
                    layoutManagerYaVistas = new LinearLayoutManager(getContext());
                    recyclerViewYaVistas.setLayoutManager(layoutManagerYaVistas);
                    recyclerViewYaVistas.setAdapter(peliculaAdapterYaVistas);
                }
            }, 1500);
        }
    }
}