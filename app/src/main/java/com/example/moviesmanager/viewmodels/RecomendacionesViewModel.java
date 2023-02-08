package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.repositories.PeliculaRepository;

import java.util.List;

public class RecomendacionesViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    public RecomendacionesViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<List<Pelicula>> getPeliculasRecomendadas() {
        return peliculaRepository.getPeliculasRecomendadas();
    }

    public void buscarPeliculasRecomendadasTMDB(Integer year, Integer valoracion, String id_genero) {
        peliculaRepository.buscarPeliculasRecomendadasTMDB(year,valoracion,id_genero);
    }
}