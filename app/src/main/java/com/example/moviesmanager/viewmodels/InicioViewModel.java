package com.example.moviesmanager.viewmodels;

import androidx.lifecycle.LiveData;
import android.content.Context;
import androidx.lifecycle.ViewModel;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.repositories.PeliculaRepository;

import java.util.List;

public class InicioViewModel extends ViewModel {

    private PeliculaRepository peliculaRepository;

    public InicioViewModel() {
        peliculaRepository = PeliculaRepository.getInstance();
    }

    public LiveData<List<Favorita>> getFavoritas(){
        return peliculaRepository.getFavoritas();
    }

    public LiveData<List<VerMasTarde>> getVerMasTarde(){
        return peliculaRepository.getVerMasTarde();
    }

    public LiveData<List<Pelicula>> getPeliculasFavoritas(){
        return peliculaRepository.getPeliculasFavoritas();
    }

    public LiveData<List<Pelicula>> getPeliculasVerMasTarde(){
        return peliculaRepository.getPeliculasVerMasTarde();
    }


    public void obtenerFavoritas(Context context){
        peliculaRepository.obtenerFavoritas(context);
    }

    public void obtenerVerMasTarde(Context context){
        peliculaRepository.obtenerVerMasTarde(context);
    }

    public void obtenerYaVistas(Context context) {
        peliculaRepository.obtenerYaVistas(context);
    }


    public void obtenerPeliculasFavoritas(List<Integer> idsPeliculas) {
        peliculaRepository.obtenerPeliculasFavoritas(idsPeliculas);
    }

    public void obtenerPeliculasVerMasTarde(List<Integer> idsPeliculas) {
        peliculaRepository.obtenerPeliculasVerMasTarde(idsPeliculas);
    }

    public LiveData<List<YaVista>> getYaVistas() {
        return peliculaRepository.getYaVistas();
    }
}