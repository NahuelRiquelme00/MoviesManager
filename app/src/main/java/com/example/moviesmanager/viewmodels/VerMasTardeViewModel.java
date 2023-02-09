package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.repositories.PeliculaRepository;

import java.util.List;

public class VerMasTardeViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    public VerMasTardeViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<List<VerMasTarde>> getVerMasTarde() {
        return peliculaRepository.getVerMasTarde();
    }

    public LiveData<List<Pelicula>> getPeliculasVerMasTarde() {
        return peliculaRepository.getPeliculasVerMasTarde();
    }

    public void obtenerPeliculasVerMasTarde(List<Integer> idsPeliculas) {
        peliculaRepository.obtenerPeliculasVerMasTarde(idsPeliculas);

    }

    public void ordenarPeliculasVerMasTarde(int i) {
        peliculaRepository.ordenarPeliculasVerMasTarde(i);
    }
}