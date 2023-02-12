package com.example.moviesmanager.views.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmanager.R;
import com.example.moviesmanager.adapter.PeliculaAdapterInicio;
import com.example.moviesmanager.databinding.FragmentInicioBinding;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.viewmodels.InicioViewModel;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private RecyclerView recyclerViewInicioFavoritas;
    private PeliculaAdapterInicio peliculaAdapterInicioFavoritas;
    private LinearLayoutManager layoutManagerInicioFavoritas;
    private RecyclerView recyclerViewInicioVerMasTarde;
    private PeliculaAdapterInicio peliculaAdapterInicioVerMasTarde;
    private LinearLayoutManager layoutManagerInicioVerMasTarde;
    private Button mYaVistas;
    private Button mRecomendaciones;
    private InicioViewModel inicioViewModel;
    private LinearLayout layoutFavoritas;
    private LinearLayout layoutVerMasTarde;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //View model
        inicioViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(InicioViewModel.class);

        //Binding
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mYaVistas = binding.buttonInicioYaVistas;
        mRecomendaciones = binding.buttonInicioRecomendaciones;
        recyclerViewInicioVerMasTarde = binding.recyclerViewInicioVerMasTarde;
        recyclerViewInicioFavoritas = binding.recyclerViewInicioFavoritas;
        layoutFavoritas = binding.inicioLayoutFavoritas;
        layoutVerMasTarde = binding.inicioLayoutVerMasTarde;

        //Obtener favoritas y ver mas tarde
        inicioViewModel.obtenerFavoritas(getContext());
        inicioViewModel.obtenerVerMasTarde(getContext());
        inicioViewModel.obtenerYaVistas(getContext());

        //Observer
        ObservarCambios();

        //Configuracion recyclers
        ConfigurarRecyclerViewFavs();
        ConfigurarRecyclerViewVerMasTarde();

        //Botones
        mYaVistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_inicio_to_nav_ya_vistas);
            }
        });
        mRecomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_inicio_to_nav_recomendaciones);
            }
        });

        return root;
    }

    private void ObservarCambios() {
        inicioViewModel.getFavoritas().observe((LifecycleOwner) getContext(), new Observer<List<Favorita>>() {
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
        inicioViewModel.getPeliculasFavoritas().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                if(peliculas != null){
                    recyclerViewInicioFavoritas.setAdapter(new PeliculaAdapterInicio(peliculas, getContext()));
                }
            }
        });
        inicioViewModel.getVerMasTarde().observe((LifecycleOwner) getContext(), new Observer<List<VerMasTarde>>() {
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
        inicioViewModel.getPeliculasVerMasTarde().observe((LifecycleOwner) getContext(), new Observer<List<Pelicula>>() {
            @Override
            public void onChanged(List<Pelicula> peliculas) {
                if(peliculas != null){
                    recyclerViewInicioVerMasTarde.setAdapter(new PeliculaAdapterInicio(peliculas,getContext()));
                }
            }
        });
    }

    private void obtenerPeliculasFavoritas(List<Integer> idsPeliculas) {
        if(idsPeliculas.isEmpty()){
            layoutFavoritas.setVisibility(View.VISIBLE);
        } else {
            layoutFavoritas.setVisibility(View.GONE);
        }
        inicioViewModel.obtenerPeliculasFavoritas(idsPeliculas);
    }

    private void obtenerPeliculasVerMasTarde(List<Integer> idsPeliculas){
        if(idsPeliculas.isEmpty()){
            layoutVerMasTarde.setVisibility(View.VISIBLE);
        } else {
            layoutVerMasTarde.setVisibility(View.GONE);
        }
        inicioViewModel.obtenerPeliculasVerMasTarde(idsPeliculas);
    }

    private void ConfigurarRecyclerViewVerMasTarde() {
        peliculaAdapterInicioVerMasTarde= new PeliculaAdapterInicio(getContext().getApplicationContext());
        recyclerViewInicioVerMasTarde.setHasFixedSize(true);
        layoutManagerInicioVerMasTarde = new LinearLayoutManager(getContext());
        layoutManagerInicioVerMasTarde.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewInicioVerMasTarde.setLayoutManager(layoutManagerInicioVerMasTarde);
        recyclerViewInicioVerMasTarde.setAdapter(peliculaAdapterInicioVerMasTarde);
    }

    private void ConfigurarRecyclerViewFavs() {
        peliculaAdapterInicioFavoritas = new PeliculaAdapterInicio(getContext().getApplicationContext());
        recyclerViewInicioFavoritas.setHasFixedSize(true);
        layoutManagerInicioFavoritas = new LinearLayoutManager(getContext());
        layoutManagerInicioFavoritas.setOrientation(LinearLayoutManager.HORIZONTAL);;
        recyclerViewInicioFavoritas.setLayoutManager(layoutManagerInicioFavoritas);
        recyclerViewInicioFavoritas.setAdapter(peliculaAdapterInicioFavoritas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}