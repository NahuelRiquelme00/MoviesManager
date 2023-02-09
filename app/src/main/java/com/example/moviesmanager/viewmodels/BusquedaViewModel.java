package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.repositories.PeliculaRepository;

import java.util.List;

public class BusquedaViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;


    public BusquedaViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<List<Pelicula>> getPeliculas() {
        return peliculaRepository.getPeliculas();
    }

    //3 - se llama al metodo desde el viewmodel
    public void buscarPeliculaTMDB(String consulta){
        peliculaRepository.buscarPeliculaTMDB(consulta);
    }
}