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

    @Query("SELECT foto FROM usuario WHERE idUsuario = :id")
    byte[] obtenerFotoPerfilPath(Integer id);

    @Insert
    void insertarUsuario(Usuario usuario);

    @Delete
    void eliminarUsuario(Usuario usuario);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM usuario WHERE idUsuario = :id) > 0 THEN 1 ELSE 0 END")
    boolean existsById(int id);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM usuario WHERE idUsuario = :id AND nombre IS NULL) > 0 THEN 1 ELSE 0 END")
    boolean usuarioNoCargado(int id);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM usuario WHERE idUsuario = :id AND correo IS NULL) > 0 THEN 1 ELSE 0 END")
    boolean correoNoCargado(int id);

    @Query("SELECT CASE WHEN (SELECT COUNT(*) FROM usuario WHERE idUsuario = :id AND foto IS NULL) > 0 THEN 1 ELSE 0 END")
    boolean fotoNoCargada(int id);

    @Query("UPDATE usuario SET nombre = :nombre WHERE idUsuario = :id")
    void actualizarNombre(Integer id, String nombre);

    @Query("UPDATE usuario SET correo = :correo WHERE idUsuario = :id")
    void actualizarCorreo(Integer id, String correo);

    @Query("UPDATE usuario SET foto = :foto WHERE idUsuario = :id")
    void actualizarFoto(Integer id, byte [] foto);

}
