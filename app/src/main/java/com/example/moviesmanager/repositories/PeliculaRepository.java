package com.example.moviesmanager.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.moviesmanager.database.ClienteDB;
import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.network.ClienteTMDB;

import java.util.List;

public class PeliculaRepository {

    private static PeliculaRepository instance;

    private final ClienteTMDB clienteTMDB;
    private final ClienteDB clienteDB;

    public static PeliculaRepository getInstance(){
        if(instance==null){
            instance = new PeliculaRepository();
        }
        return instance;
    }

    public PeliculaRepository() {
        clienteTMDB = ClienteTMDB.getInstance();
        clienteDB = ClienteDB.getInstance();
    }

    public LiveData<List<Pelicula>> getPeliculas(){
        return clienteTMDB.getPeliculas();
    }

    public LiveData<Pelicula> getDetalles() {
        return clienteTMDB.getDetalles();
    }

    public LiveData<List<Favorita>> getFavoritas() {
        return clienteDB.getFavoritas();
    }

    public LiveData<List<VerMasTarde>> getVerMasTarde() {
        return clienteDB.getVerMasTarde();
    }

    public LiveData<List<YaVista>> getYaVistas() {
        return clienteDB.getYavistas();
    }

    public LiveData<List<Pelicula>> getPeliculasFavoritas() {
        return clienteTMDB.getPeliculasFavoritas();
    }

    public LiveData<List<Pelicula>> getPeliculasVerMasTarde() {
        return clienteTMDB.getPeliculasVerMasTarde();
    }

    public LiveData<List<Pelicula>> getPeliculasYaVistas() {
        return clienteTMDB.getPeliculasYaVistas();
    }

    public LiveData<List<Pelicula>> getPeliculasRecomendadas() {
        return clienteTMDB.getPeliculasRecomendadas();
    }

    public void ordenarPeliculasFavoritas(int i){
        clienteTMDB.ordenarPeliculasFavoritas(i);
    }

    public void ordenarPeliculasVerMasTarde(int i) {
        clienteTMDB.ordenarPeliculasVerMasTarde(i);
    }

    public void ordenarPeliculasYaVistas(int i) {
        clienteTMDB.ordenarPeliculasYaVistas(i);
    }



    //2 - Se llama al metodo desde el repositorio
    public void buscarPeliculaTMDB(String consulta){
        clienteTMDB.buscarPeliculasTMBD(consulta);
    }

    public void buscarPeliculasRecomendadasTMDB(Integer year, Integer valoracion, String id_genero) {
        clienteTMDB.buscarPeliculasRecomendadasTMBD(year,valoracion,id_genero);
    }

    public void obtenerDetalles(Integer id_pelicula) {
        clienteTMDB.obtenerDetalles(id_pelicula);
    }


    public void obtenerFavoritas(Context context) { clienteDB.obtenerFavoritas(context);
    }

    public void obtenerVerMasTarde(Context context) { clienteDB.obtenerVerMasTarde(context);
    }

    public void obtenerYaVistas(Context context) {
        clienteDB.obtenerYaVistas(context);
    }


    public void obtenerPeliculasFavoritas(List<Integer> idsPeliculas) {
        clienteTMDB.obtenerPeliculasFavoritas(idsPeliculas);
    }

    public void obtenerPeliculasVerMasTarde(List<Integer> idsPeliculas) {
        clienteTMDB.obtenerPeliculasVerMasTarde(idsPeliculas);
    }

    public void obtenerPeliculasYaVistas(List<Integer> idsPeliculas) {
        clienteTMDB.obtenerPeliculasYaVistas(idsPeliculas);
    }
}
