package com.example.moviesmanager.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.repositories.PeliculaRepository;

import java.util.List;

public class YaVistasViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    public YaVistasViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<List<YaVista>> getYaVistas() {
        return peliculaRepository.getYaVistas();
    }

    public LiveData<List<Pelicula>> getPeliculasYaVistas() {
        return peliculaRepository.getPeliculasYaVistas();
    }

    public void obtenerPeliculasYaVistas(List<Integer> idsPeliculas) {
        peliculaRepository.obtenerPeliculasYaVistas(idsPeliculas);
    }

    public void ordenarPeliculasYaVistas(int i) {
        peliculaRepository.ordenarPeliculasYaVistas(i);
    }

    public void obtenerYavistas(Context context) {
        peliculaRepository.obtenerYaVistas(context);
    }
}