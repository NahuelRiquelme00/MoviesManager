package com.example.moviesmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesmanager.models.Favorita;

import java.util.List;

@Dao
public interface DaoFavorita {

    @Query("SELECT * FROM favorita")
    List<Favorita> obtenerPeliculas();

    @Insert
    void insertarPelicula(Favorita favorita);

    @Delete
    void eliminarPelicula(Favorita favorita);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM favorita WHERE idPelicula = :id) > 0 THEN 1 ELSE 0 END")
    boolean existsById(int id);


}
