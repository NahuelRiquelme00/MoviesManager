package com.example.moviesmanager.network;

import com.example.moviesmanager.utils.Credenciales;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultarTMDB {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Credenciales.API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PeticionesTMDB peliculasApi = retrofit.create(PeticionesTMDB.class);

    public static PeticionesTMDB getPeliculasApi(){
        return peliculasApi;
    }
}
