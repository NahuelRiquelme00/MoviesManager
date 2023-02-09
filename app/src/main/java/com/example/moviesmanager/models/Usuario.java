package com.example.moviesmanager.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    //Atributos
    @PrimaryKey
    @NonNull
    private Integer idUsuario;

    @ColumnInfo(name = "NOMBRE")
    private String nombre;
    @ColumnInfo(name = "CORREO")
    private String correo;
    @ColumnInfo(name = "FOTO", typeAffinity = ColumnInfo.BLOB)
    private byte [] fotoPerfilPath;

    //Constructor
    public Usuario(@NonNull Integer idUsuario){
        this.idUsuario = idUsuario;
    }

    //Getters y Setters
    @NonNull
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(@NonNull Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getFotoPerfilPath() {
        return fotoPerfilPath;
    }

    public void setFotoPerfilPath(byte[] fotoPerfilPath) {
        this.fotoPerfilPath = fotoPerfilPath;
    }
}
