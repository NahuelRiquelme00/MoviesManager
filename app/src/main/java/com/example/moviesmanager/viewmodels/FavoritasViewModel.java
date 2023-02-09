package com.example.moviesmanager.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.repositories.PeliculaRepository;

import java.util.List;

public class FavoritasViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    public FavoritasViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<List<Favorita>> getFavoritas(){
        return peliculaRepository.getFavoritas();
    }

    public LiveData<List<Pelicula>> getPeliculasFavoritas(){
        return peliculaRepository.getPeliculasFavoritas();
    }

    public void obtenerFavoritas(Context context){
        peliculaRepository.obtenerFavoritas(context);
    }


    public void obtenerPeliculasFavoritas(List<Integer> idsPeliculas) {
        peliculaRepository.obtenerPeliculasFavoritas(idsPeliculas);
    }

    public void ordenarPeliculasFavoritas(int i){
        peliculaRepository.ordenarPeliculasFavoritas(i);
    }


}