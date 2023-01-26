package com.example.moviesmanager.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Review {

    @PrimaryKey
    @NonNull
    private Integer idPelicula;

    private String review;

    public Review(@NonNull Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    @NonNull
    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(@NonNull Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
