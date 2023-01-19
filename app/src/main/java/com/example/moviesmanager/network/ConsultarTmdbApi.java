package com.example.moviesmanager.network;

import android.util.Log;

import com.example.moviesmanager.models.Pelicula;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultarTmdbApi {
    public static Retrofit retrofit;
    public static String API_URL = "https://api.themoviedb.org/3/";
    public static String API_KEY = "db4d382cec1aeb8010fb784d2ef9fd30";
    public static String POSTER_PATH = "https://image.tmdb.org/t/p/w500";

    public List<Pelicula> respuesta (String title){
        retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        PeticionesApiPeliculas consApi = retrofit.create(PeticionesApiPeliculas.class);

        Call<JsonObject> call = consApi.getPeliculas(API_KEY,title);

        List<Pelicula> listaPeliculas = new ArrayList<>();

        call.enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    if(response.isSuccessful()){

                        JsonArray result_array = response.body().getAsJsonArray("results");

                        List<ResultSearchMovie> peliculas = new Gson().fromJson(result_array,new TypeToken<List<ResultSearchMovie>>(){}.getType());



                        for(ResultSearchMovie pelicula : peliculas){
                            //Para probar si funciona la consulta a la API
                            Log.d("Movies", pelicula.getTitle());
                            //Cargo los datos a mostrar de los resultados en la pelicula y luego la cargo en la lista
                            Pelicula peliculaActual = new Pelicula();
                            peliculaActual.setTitulo(pelicula.getTitle());
                            peliculaActual.setFechaDeEstreno(pelicula.getReleaseDate());
                            peliculaActual.setPosterPath(POSTER_PATH + pelicula.getPosterPath());
                            //Cargar atributos faltantes
                            listaPeliculas.add(peliculaActual);
                        }
                    }else{
                        System.out.println("Mal");
                        System.out.println(call);
                    }

                }catch (Exception ex){
                    System.out.println("Paso esto en on response: " + ex);
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Paso esto en on failure: " + t);
            }
        });

        return listaPeliculas;
    }
}
