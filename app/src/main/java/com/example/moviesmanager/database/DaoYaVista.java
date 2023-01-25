package com.example.moviesmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.YaVista;

import java.util.List;

@Dao
public interface DaoYaVista {

    @Query("SELECT * FROM yaVista")
    List<YaVista> obtenerPeliculas();

    @Insert
    void insertarPelicula(YaVista yaVista);

    @Delete
    void eliminarPelicula(YaVista yaVista);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM yavista WHERE idPelicula = :id) > 0 THEN 1 ELSE 0 END")
    boolean existsById(int id);


}
