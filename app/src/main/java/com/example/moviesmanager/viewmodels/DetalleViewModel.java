package com.example.moviesmanager.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.repositories.PeliculaRepository;

public class DetalleViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    public DetalleViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<Pelicula> getDetalles() {
        return peliculaRepository.getDetalles();
    }

    public void obtenerDetalles(Integer id_pelicula) {
        peliculaRepository.obtenerDetalles(id_pelicula);
    }

    public void obtenerFavoritas(Context context) {
        peliculaRepository.obtenerFavoritas(context);
    }

    public void obtenerYaVistas(Context context) {
        peliculaRepository.obtenerYaVistas(context);
    }

    public void obtenerVerMasTarde(Context context) {
        peliculaRepository.obtenerVerMasTarde(context);
    }
}