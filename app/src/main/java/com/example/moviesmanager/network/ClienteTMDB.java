package com.example.moviesmanager.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesmanager.models.Pelicula;
import com.example.moviesmanager.utils.AppExecutors;
import com.example.moviesmanager.utils.Credenciales;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ClienteTMDB {

    //LiveData para la lista de peliculas en el fragmento de busqueda
    private MutableLiveData<List<Pelicula>> mPeliculas;
    //LiveData para los detalles de una pelicula en el fragmento de detalle
    private MutableLiveData<Pelicula> mPeliculaDetalles;
    //LiveData para la lista de peliculas favoritas
    private MutableLiveData<List<Pelicula>> mPeliculasFavoritas;
    //LiveData para la lista de peliculas a ver mas tarde
    private MutableLiveData<List<Pelicula>> mPeliculasVerMasTarde;
    //LiveData para la lista de peliculas ya vistas
    private MutableLiveData<List<Pelicula>> mPeliculasYaVistas;
    //LiveData para la lista de peliculas recomendadas
    private MutableLiveData<List<Pelicula>> mPeliculasRecomendadas;

    //Para ordenar listas de peliculas
    private List<Pelicula> peliculasOrdenadas;

    private static ClienteTMDB instance;
    private RecibirPeliculasRunnable recibirPeliculasRunnable;
    private RecibirDetallesPeliculaRunnable recibirDetallesPeliculaRunnable;
    private RecibirFavoritasPeliculaRunnable recibirFavoritasPeliculaRunnable;
    private RecibirVerMasTardePeliculaRunnable recibirVerMasTardePeliculaRunnble;
    private RecibirYaVistasPeliculaRunnable recibirYaVistasPeliculaRunnable;
    private RecibirPeliculasRecomendadasRunnable recibirPeliculasRecomendadasRunnable;

    public static ClienteTMDB getInstance(){
        if(instance==null){
            instance = new ClienteTMDB();
        }
        return instance;
    }

    public ClienteTMDB() {
        this.mPeliculas = new MutableLiveData<>();
        this.mPeliculaDetalles = new MutableLiveData<>();
        this.mPeliculasFavoritas = new MutableLiveData<>();
        this.mPeliculasVerMasTarde = new MutableLiveData<>();
        this.mPeliculasYaVistas = new MutableLiveData<>();
        this.mPeliculasRecomendadas = new MutableLiveData<>();
    }

    public LiveData<List<Pelicula>> getPeliculas() {
        return mPeliculas;
    }

    public LiveData<List<Pelicula>> getPeliculasRecomendadas() {
        return mPeliculasRecomendadas;
    }

    public LiveData<Pelicula> getDetalles() {
        return mPeliculaDetalles;
    }

    public LiveData<List<Pelicula>> getPeliculasFavoritas() {
        return mPeliculasFavoritas;
    }

    public LiveData<List<Pelicula>> getPeliculasVerMasTarde() {
        return mPeliculasVerMasTarde;
    }

    public LiveData<List<Pelicula>> getPeliculasYaVistas() { return  mPeliculasYaVistas; }

    public void ordenarPeliculasFavoritas(int i){
        switch (i) {
                    case 1:
                        //Ascendente
                        peliculasOrdenadas = mPeliculasFavoritas.getValue();
                        peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getTitle));
                        mPeliculasFavoritas.setValue(peliculasOrdenadas);
                        break;
                    case 2:
                        //Descendente
                        peliculasOrdenadas = mPeliculasFavoritas.getValue();
                        peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getTitle).reversed());
                        mPeliculasFavoritas.setValue(peliculasOrdenadas);
                        break;
                    case 3:
                        //Menor duracion
                        peliculasOrdenadas = mPeliculasFavoritas.getValue();
                        peliculasOrdenadas.sort(Comparator.comparing((Pelicula::getDuracionInteger)));
                        mPeliculasFavoritas.setValue(peliculasOrdenadas);
                        break;
                    case 4:
                        //Mayor duracion
                        peliculasOrdenadas = mPeliculasFavoritas.getValue();
                        peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getDuracionInteger).reversed());
                        mPeliculasFavoritas.setValue(peliculasOrdenadas);
                        break;
                    case 5:
                        //Mas reciente
                        peliculasOrdenadas = mPeliculasFavoritas.getValue();
                        peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getRelease_date).reversed());
                        mPeliculasFavoritas.setValue(peliculasOrdenadas);
                        break;
                    case 6:
                        //Mas antigua
                        peliculasOrdenadas = mPeliculasFavoritas.getValue();
                        peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getRelease_date));
                        mPeliculasFavoritas.setValue(peliculasOrdenadas);
                        break;
                    default:
                        break;
                }
    }

    public void ordenarPeliculasVerMasTarde(int i) {
        switch (i) {
            case 1:
                //Ascendente
                peliculasOrdenadas = mPeliculasVerMasTarde.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getTitle));
                mPeliculasVerMasTarde.setValue(peliculasOrdenadas);
                break;
            case 2:
                //Descendente
                peliculasOrdenadas = mPeliculasVerMasTarde.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getTitle).reversed());
                mPeliculasVerMasTarde.setValue(peliculasOrdenadas);
                break;
            case 3:
                //Menor duracion
                peliculasOrdenadas = mPeliculasVerMasTarde.getValue();
                peliculasOrdenadas.sort(Comparator.comparing((Pelicula::getDuracionInteger)));
                mPeliculasVerMasTarde.setValue(peliculasOrdenadas);
                break;
            case 4:
                //Mayor duracion
                peliculasOrdenadas = mPeliculasVerMasTarde.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getDuracionInteger).reversed());
                mPeliculasVerMasTarde.setValue(peliculasOrdenadas);
                break;
            case 5:
                //Mas reciente
                peliculasOrdenadas = mPeliculasVerMasTarde.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getRelease_date).reversed());
                mPeliculasVerMasTarde.setValue(peliculasOrdenadas);
                break;
            case 6:
                //Mas antigua
                peliculasOrdenadas = mPeliculasVerMasTarde.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getRelease_date));
                mPeliculasVerMasTarde.setValue(peliculasOrdenadas);
                break;
            default:
                break;
        }
    }

    public void ordenarPeliculasYaVistas(int i){
        switch (i) {
            case 1:
                //Ascendente
                peliculasOrdenadas = mPeliculasYaVistas.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getTitle));
                mPeliculasYaVistas.setValue(peliculasOrdenadas);
                break;
            case 2:
                //Descendente
                peliculasOrdenadas = mPeliculasYaVistas.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getTitle).reversed());
                mPeliculasYaVistas.setValue(peliculasOrdenadas);
                break;
            case 3:
                //Menor duracion
                peliculasOrdenadas = mPeliculasYaVistas.getValue();
                peliculasOrdenadas.sort(Comparator.comparing((Pelicula::getDuracionInteger)));
                mPeliculasYaVistas.setValue(peliculasOrdenadas);
                break;
            case 4:
                //Mayor duracion
                peliculasOrdenadas = mPeliculasYaVistas.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getDuracionInteger).reversed());
                mPeliculasYaVistas.setValue(peliculasOrdenadas);
                break;
            case 5:
                //Mas reciente
                peliculasOrdenadas = mPeliculasYaVistas.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getRelease_date).reversed());
                mPeliculasYaVistas.setValue(peliculasOrdenadas);
                break;
            case 6:
                //Mas antigua
                peliculasOrdenadas = mPeliculasYaVistas.getValue();
                peliculasOrdenadas.sort(Comparator.comparing(Pelicula::getRelease_date));
                mPeliculasYaVistas.setValue(peliculasOrdenadas);
                break;
            default:
                break;
        }
    }

    //Hilo para recibir peliculas desde TMDB
    public void buscarPeliculasTMBD(String consulta){

        if(recibirPeliculasRunnable != null){
            recibirPeliculasRunnable = null;
        }

        recibirPeliculasRunnable = new RecibirPeliculasRunnable(consulta);

        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirPeliculasRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelar la llamada a retrofit
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);
    }

    //Hilo para recibir peliculas recomendadas desde TMDB
    public void buscarPeliculasRecomendadasTMBD(Integer year, Integer valoracion, String id_genero){

        if(recibirPeliculasRecomendadasRunnable != null){
            recibirPeliculasRecomendadasRunnable = null;
        }

        recibirPeliculasRecomendadasRunnable = new RecibirPeliculasRecomendadasRunnable(year, valoracion, id_genero);

        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirPeliculasRecomendadasRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelar la llamada a retrofit
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);
    }

    //Hilo para recibir detalles de un pelicula desde TMDB
    public void obtenerDetalles (Integer id_pelicula){

        if(recibirDetallesPeliculaRunnable!= null){
            recibirDetallesPeliculaRunnable = null;
        }

        recibirDetallesPeliculaRunnable = new RecibirDetallesPeliculaRunnable(id_pelicula);

        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirDetallesPeliculaRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelar la llamada a retrofit
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);
    }

    public void obtenerPeliculasFavoritas(List<Integer> idsPeliculas){
        if(recibirFavoritasPeliculaRunnable != null){
            recibirFavoritasPeliculaRunnable = null;
        }
        recibirFavoritasPeliculaRunnable = new RecibirFavoritasPeliculaRunnable(idsPeliculas);
        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirFavoritasPeliculaRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

    public void obtenerPeliculasVerMasTarde(List<Integer> idsPeliculas){
        if(recibirVerMasTardePeliculaRunnble != null){
            recibirVerMasTardePeliculaRunnble = null;
        }
        recibirVerMasTardePeliculaRunnble = new RecibirVerMasTardePeliculaRunnable(idsPeliculas);
        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirVerMasTardePeliculaRunnble);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

    public void obtenerPeliculasYaVistas(List<Integer> idsPeliculas){
        if(recibirYaVistasPeliculaRunnable != null){
            recibirYaVistasPeliculaRunnable = null;
        }
        recibirYaVistasPeliculaRunnable = new RecibirYaVistasPeliculaRunnable(idsPeliculas);
        final Future myHandler = AppExecutors.getInstance().getNetwork().submit(recibirYaVistasPeliculaRunnable);

        AppExecutors.getInstance().getNetwork().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

    //1 - Este es el metodo que se llama desde el fragmento de busqueda
    //Recibir datos desde retrofit con la clase runnable
    //Tenemos 3 tipos de consultas: por id, por nombre, por recomendacion
    public class RecibirPeliculasRunnable implements Runnable{
        private String consulta;
        boolean cancelarPeticion;

        public RecibirPeliculasRunnable(String consulta) {
            this.consulta = consulta;
            this.cancelarPeticion = false;
        }

        @Override
        public void run() {
            try{
                Response response = getPeliculas(consulta).execute();
                if(cancelarPeticion){
                    return;
                }
                if(response.code() == 200){
                    JsonObject resultado = (JsonObject) response.body();
                    JsonArray result_array = resultado.getAsJsonArray("results");
                    List<Pelicula> listaPeliculas = new ArrayList<>();
                    List<ResultSearchTitleMovie> peliculas = new Gson().fromJson(result_array,new TypeToken<List<ResultSearchTitleMovie>>(){}.getType());
                    for(ResultSearchTitleMovie pelicula : peliculas){
                        Log.d("Movies", pelicula.getTitle());

                        Pelicula peliculaActual = new Pelicula();
                        peliculaActual.setId(pelicula.getId());
                        peliculaActual.setTitle(pelicula.getTitle());
                        peliculaActual.setRelease_date(pelicula.getReleaseDate());
                        peliculaActual.setPoster_path(Credenciales.POSTER_PATH + pelicula.getPosterPath());
                        listaPeliculas.add(peliculaActual);
                    }
                    mPeliculas.postValue(listaPeliculas);
                }else{
                    String  error = response.errorBody().string();
                    Log.v("Tag","Error " + error);
                    mPeliculas.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.v("Tag","Error " + e);
                mPeliculas.postValue(null);
            }
        }

        //Consulta de busqueda por nombre
        private Call<JsonObject> getPeliculas(String consulta){
            return ConsultarTMDB.getPeliculasApi().getPeliculas(
                    Credenciales.API_KEY,
                    consulta
            );
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }

    }

    public class RecibirPeliculasRecomendadasRunnable implements Runnable{
        private Integer year;
        private Integer valoracion;
        private String id_genero;
        boolean cancelarPeticion;

        public RecibirPeliculasRecomendadasRunnable(Integer year, Integer valoracion, String id_genero) {
            this.year = year;
            this.valoracion = valoracion;
            this.id_genero = id_genero;
            this.cancelarPeticion = false;
        }

        @Override
        public void run() {
            try{
                Response response = getRecomendaciones(year,valoracion,id_genero).execute();
                if(cancelarPeticion){
                    return;
                }
                if(response.code() == 200){
                    JsonObject resultado = (JsonObject) response.body();
                    JsonArray result_array = resultado.getAsJsonArray("results");
                    List<Pelicula> listaPeliculas = new ArrayList<>();
                    List<ResultSearchTitleMovie> peliculas = new Gson().fromJson(result_array,new TypeToken<List<ResultSearchTitleMovie>>(){}.getType());
                    for(ResultSearchTitleMovie pelicula : peliculas){
                        Log.d("Movies", pelicula.getTitle());

                        Pelicula peliculaActual = new Pelicula();
                        peliculaActual.setId(pelicula.getId());
                        peliculaActual.setTitle(pelicula.getTitle());
                        peliculaActual.setRelease_date(pelicula.getReleaseDate());
                        peliculaActual.setPoster_path(Credenciales.POSTER_PATH + pelicula.getPosterPath());
                        listaPeliculas.add(peliculaActual);
                    }
                    mPeliculasRecomendadas.postValue(listaPeliculas);
                }else{
                    String  error = response.errorBody().string();
                    Log.v("Tag","Error " + error);
                    mPeliculasRecomendadas.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.v("Tag","Error " + e);
                mPeliculasRecomendadas.postValue(null);
            }

        }

        //Consultar recomendaciones
        private Call<JsonObject> getRecomendaciones(Integer year, Integer valoracion, String id_genero){
            return ConsultarTMDB.getPeliculasApi().getRecomendaciones(
                    Credenciales.API_KEY,
                    year,
                    valoracion,
                    id_genero
            );
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

    public class RecibirDetallesPeliculaRunnable implements Runnable{
        private Integer id_pelicula;
        boolean cancelarPeticion;

        public RecibirDetallesPeliculaRunnable(Integer id_pelicula) {
            this.id_pelicula = id_pelicula;
            this.cancelarPeticion = false;
        }

        @Override
        public void run() {
            try{
                Response response = getDetalles(id_pelicula).execute();
                if(cancelarPeticion){
                    return;
                }
                if(response.code() == 200){
                    Pelicula mCargarDetalles= new Pelicula();
                    JsonObject result_detalle = (JsonObject) response.body();
                    ResultDetalles detalles = new Gson().fromJson(result_detalle, new TypeToken<ResultDetalles>(){}.getType());
                    JsonArray result_genre = result_detalle.getAsJsonArray("genres");
                    List<Genre> genre = new Gson().fromJson(result_genre, new TypeToken<List<Genre>>(){}.getType());
                    JsonObject result_credits = result_detalle.getAsJsonObject("credits");
                    JsonArray result_crew = result_credits.getAsJsonArray("crew");
                    List<Crew> crew = new Gson().fromJson(result_crew, new TypeToken<List<Crew>>(){}.getType());
                    Optional<Crew> director = crew.stream().filter(c -> c.getJob().equals("Director")).findFirst();

                    mCargarDetalles.setId(detalles.getId());
                    mCargarDetalles.setTitle(detalles.getTitle());
                    mCargarDetalles.setGenero(genre.get(0).getName());
                    if(director.isPresent()){
                        mCargarDetalles.setDirector(director.get().getName());
                    }
                    mCargarDetalles.setRelease_date(detalles.getReleaseDate());
                    mCargarDetalles.setPoster_path(Credenciales.POSTER_PATH + detalles.getPosterPath());
                    mCargarDetalles.setDescripcion(detalles.getOverview());
                    mCargarDetalles.setDuracion(detalles.getRuntime().toString());

                    mPeliculaDetalles.postValue(mCargarDetalles);
                }else{
                    String  error = response.errorBody().string();
                    Log.v("Tag","Error " + error);
                    mPeliculaDetalles.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.v("Tag","Error " + e);
                mPeliculaDetalles.postValue(null);
            }
        }

        //Obtener detalles
        private Call<JsonObject> getDetalles(Integer id_pelicula){
            return ConsultarTMDB.getPeliculasApi().getDetalles(
                    id_pelicula,
                    Credenciales.API_KEY,
                    "credits"
            );
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

    public class RecibirFavoritasPeliculaRunnable implements Runnable{
        List<Integer> idsPeliculas;
        boolean cancelarPeticion;

        public RecibirFavoritasPeliculaRunnable(List<Integer> idsPeliculas) {
            this.cancelarPeticion = false;
            this.idsPeliculas = idsPeliculas;
        }

        @Override
        public void run() {
            List<Pelicula> cargarFavoritas = new ArrayList<>();
            for(Integer id : idsPeliculas){

                try{
                    Response response = getDetalles(id).execute();
                    if(cancelarPeticion){
                        return;
                    }
                    if(response.code() == 200){
                        Pelicula mCargarDetalles= new Pelicula();
                        JsonObject result_detalle = (JsonObject) response.body();
                        ResultDetalles detalles = new Gson().fromJson(result_detalle, new TypeToken<ResultDetalles>(){}.getType());

                        mCargarDetalles.setId(detalles.getId());
                        mCargarDetalles.setTitle(detalles.getTitle());
                        mCargarDetalles.setRelease_date(detalles.getReleaseDate());
                        mCargarDetalles.setPoster_path(Credenciales.POSTER_PATH + detalles.getPosterPath());
                        mCargarDetalles.setDuracion(detalles.getRuntime().toString());

                        cargarFavoritas.add(mCargarDetalles);
                    }else{
                        String  error = response.errorBody().string();
                        Log.v("Tag","Error " + error);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mPeliculasFavoritas.postValue(cargarFavoritas);
        }

        //Consultar a TMDB las peliculas favoritas
        private Call<JsonObject> getDetalles(Integer id_pelicula){
            return ConsultarTMDB.getPeliculasApi().getDetalles(
                    id_pelicula,
                    Credenciales.API_KEY,
                    null
            );
        }


        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

    public class RecibirVerMasTardePeliculaRunnable implements Runnable{
        List<Integer> idsPeliculas;
        boolean cancelarPeticion;

        public RecibirVerMasTardePeliculaRunnable(List<Integer> idsPeliculas) {
            this.cancelarPeticion = false;
            this.idsPeliculas = idsPeliculas;
        }

        @Override
        public void run() {
            List<Pelicula> cargarVerMasTarde = new ArrayList<>();
            for(Integer id : idsPeliculas){
                try{
                    Response response = getDetalles(id).execute();
                    if(cancelarPeticion){
                        return;
                    }
                    if(response.code() == 200){
                        Pelicula mCargarDetalles= new Pelicula();
                        JsonObject result_detalle = (JsonObject) response.body();
                        ResultDetalles detalles = new Gson().fromJson(result_detalle, new TypeToken<ResultDetalles>(){}.getType());

                        mCargarDetalles.setId(detalles.getId());
                        mCargarDetalles.setTitle(detalles.getTitle());
                        mCargarDetalles.setRelease_date(detalles.getReleaseDate());
                        mCargarDetalles.setPoster_path(Credenciales.POSTER_PATH + detalles.getPosterPath());
                        mCargarDetalles.setDuracion(detalles.getRuntime().toString());

                        cargarVerMasTarde.add(mCargarDetalles);
                    }else{
                        String  error = response.errorBody().string();
                        Log.v("Tag","Error " + error);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mPeliculasVerMasTarde.postValue(cargarVerMasTarde);
        }

        //Consultar a TMDB los detalles de las peliculas para ver mas tarde
        private Call<JsonObject> getDetalles(Integer id_pelicula){
            return ConsultarTMDB.getPeliculasApi().getDetalles(
                    id_pelicula,
                    Credenciales.API_KEY,
                    null
            );
        }


        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

    public class RecibirYaVistasPeliculaRunnable implements Runnable{
        List<Integer> idsPeliculas;
        boolean cancelarPeticion;

        public RecibirYaVistasPeliculaRunnable(List<Integer> idsPeliculas) {
            this.cancelarPeticion = false;
            this.idsPeliculas = idsPeliculas;
        }

        @Override
        public void run() {
            List<Pelicula> cargarYaVistas = new ArrayList<>();
            for(Integer id : idsPeliculas){
                try{
                    Response response = getDetalles(id).execute();
                    if(cancelarPeticion){
                        return;
                    }
                    if(response.code() == 200){
                        Pelicula mCargarDetalles= new Pelicula();
                        JsonObject result_detalle = (JsonObject) response.body();
                        ResultDetalles detalles = new Gson().fromJson(result_detalle, new TypeToken<ResultDetalles>(){}.getType());

                        mCargarDetalles.setId(detalles.getId());
                        mCargarDetalles.setTitle(detalles.getTitle());
                        mCargarDetalles.setRelease_date(detalles.getReleaseDate());
                        mCargarDetalles.setPoster_path(Credenciales.POSTER_PATH + detalles.getPosterPath());
                        mCargarDetalles.setDuracion(detalles.getRuntime().toString());

                        cargarYaVistas.add(mCargarDetalles);
                    }else{
                        String  error = response.errorBody().string();
                        Log.v("Tag","Error " + error);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mPeliculasYaVistas.postValue(cargarYaVistas);
        }

        //Consultar a TMDB los detalles de las peliculas ya vistas
        private Call<JsonObject> getDetalles(Integer id_pelicula){
            return ConsultarTMDB.getPeliculasApi().getDetalles(
                    id_pelicula,
                    Credenciales.API_KEY,
                    null
            );
        }

        private void cancelarPeticion(){
            cancelarPeticion = true;
        }
    }

}
