package com.example.moviesmanager.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class YaVista {

    @PrimaryKey
    @NonNull
    private Integer idPelicula;

    public YaVista(@NonNull Integer idPelicula) {
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
