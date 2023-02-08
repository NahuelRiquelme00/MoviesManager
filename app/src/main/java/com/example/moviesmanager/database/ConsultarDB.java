package com.example.moviesmanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.Review;
import com.example.moviesmanager.models.Valoracion;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.YaVista;

@Database(
        entities = {Favorita.class, VerMasTarde.class, YaVista.class, Review.class, Valoracion.class},
        version = 5,
        exportSchema = true
)
public abstract class ConsultarDB extends RoomDatabase {

    public static ConsultarDB INSTANCE;

    public static ConsultarDB getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, ConsultarDB.class, "MoviesManagerDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  INSTANCE;
    }

    public abstract DaoFavorita daoFavorita();

    public abstract DaoVerMasTarde daoVerMasTarde();

    public abstract DaoYaVista daoYaVista();

    public abstract DaoReview daoReseña();

    public abstract DaoValoracion daoValoracion();

}
