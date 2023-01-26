package com.example.moviesmanager.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Valoracion {

    @PrimaryKey
    @NonNull
    private Integer idPelicula;

    private Float valoracion;

    public Valoracion(@NonNull Integer idPelicula, Float valoracion) {
        this.idPelicula = idPelicula;
        this.valoracion = valoracion;
    }

    @NonNull
    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(@NonNull Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Float getValoracion() {
        return valoracion;
    }

    public void setValoracion(Float valoracion) {
        this.valoracion = valoracion;
    }
}
