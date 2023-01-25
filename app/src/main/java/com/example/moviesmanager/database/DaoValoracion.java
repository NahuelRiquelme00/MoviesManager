package com.example.moviesmanager.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviesmanager.models.Valoracion;

@Dao
public interface DaoValoracion {

    @Query("SELECT valoracion FROM valoracion WHERE idPelicula = :id")
    Float obtenerValoracion(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarValoracion(Valoracion valoracion);



}
