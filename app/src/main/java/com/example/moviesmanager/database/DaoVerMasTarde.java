package com.example.moviesmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.VerMasTarde;

import java.util.List;

@Dao
public interface DaoVerMasTarde {

    @Query("SELECT * FROM verMasTarde")
    List<VerMasTarde> obtenerPeliculas();

    @Insert
    void insertarPelicula(VerMasTarde pelicula);

    @Delete
    void eliminarPelicula(VerMasTarde verMasTarde);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM vermastarde WHERE idPelicula = :id) > 0 THEN 1 ELSE 0 END")
    boolean existsById(int id);
}
