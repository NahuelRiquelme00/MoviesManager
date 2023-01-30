package com.example.moviesmanager.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PeticionesApiPeliculas {
    //Ejemplo de URL
    //https://api.themoviedb.org/3/search/movie?api_key=db4d382cec1aeb8010fb784d2ef9fd30&query=Dune


    //Buscar peliculas por nombre
    @GET("search/movie") //https://api.themoviedb.org/3/search/movie?
    Call<JsonObject> getPeliculas(@Query("api_key") String key, @Query("query") String title);

    //Buscar pelicula por id para obtener sus datos y cargar los creditos para buscar obtener al director
    @GET("movie/{movie_id}?")
    Call<JsonObject> getDetalles(@Path("movie_id") Integer id,
                                 @Query("api_key") String key,
                                 @Query("append_to_response") String response);

    @GET("discover/movie")
    Call<JsonObject> getRecomendaciones(@Query("api_key") String key,
                                        @Query("primary_release_year") Integer year,
                                        @Query("vote_average.gte") Integer valoracion,
                                        @Query("with_genres") String id_gen);

}
