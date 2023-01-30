package com.example.moviesmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Review;

@Dao
public interface DaoReview {

    @Query("SELECT review FROM review WHERE idPelicula = :id")
    String obtenerReview(Integer id);

    @Insert
    void insertarReview(Review review);

    @Delete
    void eliminarReview(Review review);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM review WHERE idPelicula = :id) > 0 THEN 1 ELSE 0 END")
    boolean existsById(int id);


}
