package com.example.moviesmanager.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VerMasTarde {
    @PrimaryKey
    @NonNull
    private Integer idPelicula;

    public VerMasTarde(@NonNull Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    @NonNull
    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(@NonNull Integer idPelicula) {
        this.idPelicula = idPelicula;
    }
}
