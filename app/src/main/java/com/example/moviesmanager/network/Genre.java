package com.example.moviesmanager.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Genre {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public static final int ACCION = 28;
    public static final int AVENTURA = 12;
    public static final int ANIMACION = 16;
    public static final int COMEDIA = 35;
    public static final int CRIMEN = 80;
    public static final int DOCUMENTAL = 99;
    public static final int DRAMA = 18;
    public static final int FAMILIA = 10751;
    public static final int FANTASIA = 14;
    public static final int HISTORIA = 36;
    public static final int TERROR = 27;
    public static final int MUSICA = 10402;
    public static final int MISTERIO = 9648;
    public static final int ROMANCE = 10749;
    public static final int CIENCIA_FICCION = 878;
    public static final int SUSPENSE = 53;
    public static final int BELICA = 10752;
    public static final int WESTERN = 37;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Map<String, Integer> GENRES = new HashMap<String, Integer>() {{
        put("Acción", ACCION);
        put("Aventura", AVENTURA);
        put("Animación", ANIMACION);
        put("Comedia", COMEDIA);
        put("Crimen", CRIMEN);
        put("Documental", DOCUMENTAL);
        put("Drama", DRAMA);
        put("Familia", FAMILIA);
        put("Fantasía", FANTASIA);
        put("Historia", HISTORIA);
        put("Terror", TERROR);
        put("Música", MUSICA);
        put("Misterio", MISTERIO);
        put("Romance", ROMANCE);
        put("Ciencia ficción", CIENCIA_FICCION);
        put("Suspense", SUSPENSE);
        put("Bélica", BELICA);
        put("Western", WESTERN);
    }};

    public static int getIdFromGenre(String genre) {
        return GENRES.getOrDefault(genre, -1);
    }

}
