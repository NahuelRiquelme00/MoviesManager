package com.example.moviesmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesmanager.models.Usuario;

@Dao
public interface DaoUsuario {

    @Query("SELECT nombre FROM usuario WHERE idUsuario = :id")
    String obtenerUsuario(Integer id);

    @Query("SELECT correo FROM usuario WHERE idUsuario = :id")
    String obtenerCorreo(Integer id);

    @Insert
    void insertarUsuario(Usuario usuario);

    @Delete
    void eliminarUsuario(Usuario usuario);

}
