package com.example.moviesmanager.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesmanager.models.Favorita;
import com.example.moviesmanager.models.VerMasTarde;
import com.example.moviesmanager.models.YaVista;
import com.example.moviesmanager.utils.AppExecutors;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ClienteDB {

    //LiveData para la lista de peliculas favoritas
    private MutableLiveData<List<Favorita>> mPeliculasFavoritas;
    //LiveData para la lista de peliculas a ver mas tarde
    private MutableLiveData<List<VerMasTarde>> mPeliculasVerMasTarde;
    //LiveData para la lista de peliculas ya vistas
    private MutableLiveData<List<YaVista>> mPeliculasYaVistas;


    private static ClienteDB instance;
    private RecibirFavoritasPeliculaRunnable recibirFavoritasPeliculaRunnable;
    private Context contextFavorita;
    private RecibirVerMasTardePeliculaRunnable recibirVerMasTardePeliculaRunnble;
    private Context contextVerMasTarde;
    private RecibirYaVistasPeliculaRunnable recibirYaVistasPeliculaRunnable;
    private Context contextYaVista;

    public static ClienteDB getInstance(){
        if(instance==null){
            instance = new ClienteDB();
        }
        return instance;
    }

    public ClienteDB() {
        this.mPeliculasFavoritas = new MutableLiveData<>();
        this.mPeliculasVerMasTarde = new MutableLiveData<>();
        this.mPeliculasYaVistas = new MutableLiveData<>();
    }

    public LiveData<List<Favorita>> getFavoritas() {
        return mPeliculasFavoritas;
    }

    public LiveData<List<VerMasTarde>> getVerMasTarde() {
        return mPeliculasVerMasTarde;
    }

    public LiveData<List<YaVista>> getYavistas() {
        return mPeliculasYaVistas;
    }

    public void obtenerFavoritas(Context context){
        contextFavorita = context;
        if(recibirFavoritasPeliculaRunnable != null){
            recibirFavoritasPeliculaRunnable = null;
        }
        recibirFavoritasPeliculaRunnable = new RecibirFavoritasPeliculaRunnable();
        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirFavoritasPeliculaRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

    public void obtenerVerMasTarde(Context context){
        contextVerMasTarde = context;
        if(recibirVerMasTardePeliculaRunnble != null){
            recibirVerMasTardePeliculaRunnble = null;
        }
        recibirVerMasTardePeliculaRunnble = new RecibirVerMasTardePeliculaRunnable();
        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirVerMasTardePeliculaRunnble);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

    public void obtenerYaVistas(Context context){
        contextYaVista = context;
        if(recibirYaVistasPeliculaRunnable != null){
            recibirYaVistasPeliculaRunnable = null;
        }
        recibirYaVistasPeliculaRunnable = new RecibirYaVistasPeliculaRunnable();
        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirYaVistasPeliculaRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

    public class RecibirFavoritasPeliculaRunnable implements Runnable{
        boolean cancelarPeticion;

        public RecibirFavoritasPeliculaRunnable() {
            this.cancelarPeticion = false;
        }

        @Override
        public void run() {
            try {
                List<Favorita> listaFavoritas = getFavoritas();
                if(listaFavoritas != null){
                    mPeliculasFavoritas.postValue(listaFavoritas);
                }else{
                    mPeliculasFavoritas.postValue(null);
                }
            }catch (Exception e){
                e.printStackTrace();
                mPeliculasFavoritas.postValue(null);
            }
        }

        //Consultar a la db las favoritas
        private List<Favorita> getFavoritas(){
            return ConsultarDB.getInstance(contextFavorita).daoFavorita().obtenerPeliculas();
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

    public class RecibirVerMasTardePeliculaRunnable implements Runnable{
        boolean cancelarPeticion;

        public RecibirVerMasTardePeliculaRunnable() {
            this.cancelarPeticion = false;
        }

        @Override
        public void run() {
            try {
                List<VerMasTarde> listaVerMasTarde = getVerMasTarde();
                if(listaVerMasTarde != null){
                    mPeliculasVerMasTarde.postValue(listaVerMasTarde);
                }else{
                    mPeliculasVerMasTarde.postValue(null);
                }
            }catch (Exception e){
                e.printStackTrace();
                mPeliculasFavoritas.postValue(null);
            }
        }

        //Consultar a la db las ver mas tarde
        private List<VerMasTarde> getVerMasTarde(){
            return ConsultarDB.getInstance(contextVerMasTarde).daoVerMasTarde().obtenerPeliculas();
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

    public class RecibirYaVistasPeliculaRunnable implements Runnable{
        boolean cancelarPeticion;

        public RecibirYaVistasPeliculaRunnable() {
            this.cancelarPeticion = false;
        }

        @Override
        public void run() {
            try {
                List<YaVista> listaYaVistas = getYaVistas();
                if(listaYaVistas != null){
                    mPeliculasYaVistas.postValue(listaYaVistas);
                }else{
                    mPeliculasYaVistas.postValue(null);
                }
            }catch (Exception e){
                e.printStackTrace();
                mPeliculasYaVistas.postValue(null);
            }
        }

        //Consultar a la db las ya vistas
        private List<YaVista> getYaVistas(){
            return ConsultarDB.getInstance(contextYaVista).daoYaVista().obtenerPeliculas();
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }


}
