package com.example.moviesmanager.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.repositories.PeliculaRepository;

public class ReviewViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    private MutableLiveData<String> review;

    public ReviewViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<Pelicula> getDetalles() {
        return peliculaRepository.getDetalles();
    }

    public void obtenerDetalles(Integer id_pelicula) {
        peliculaRepository.obtenerDetalles(id_pelicula);
    }
}