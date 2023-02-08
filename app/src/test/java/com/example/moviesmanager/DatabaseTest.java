package com.example.moviesmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.moviesmanager.database.ConsultarDB;
import com.example.moviesmanager.models.Favorita;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private ConsultarDB mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ConsultarDB.class).allowMainThreadQueries().build();
    }

    @Test
    public void insertarYobtenerElemento() {
        Favorita favorita = new Favorita(500);
        mDb.daoFavorita().insertarPelicula(favorita);
        List<Favorita> favoritas = mDb.daoFavorita().obtenerPeliculas();
        assertEquals(favoritas.get(0).getIdPelicula(), Integer.valueOf(500));
    }

    @Test
    public void insertarFavorita(){
        Favorita favorita = new Favorita(600);
        mDb.daoFavorita().insertarPelicula(favorita);
        assertTrue(mDb.daoFavorita().existsById(600));
    }

    @Test
    public void ElminarFavorita(){
        Favorita favorita = new Favorita(700);
        mDb.daoFavorita().insertarPelicula(favorita);
        mDb.daoFavorita().eliminarPelicula(favorita);
        assertFalse(mDb.daoFavorita().existsById(700));
    }


    @After
    public void closeDb(){
        mDb.close();
    }

}
