package com.backend.clinicaOdontologica.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase de utilidad para imprimir objetos como cadenas JSON.
 */
public class JsonPrinter {

    /**
     * Convierte un objeto en una cadena JSON formateada.
     *
     * @param object el objeto a convertir.
     * @return la representación JSON del objeto.
     */
    public static String toString(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        // Convertir el objeto a JSON y eliminar espacios en blanco y saltos de línea
        return gson.toJson(object).trim().replace("\n", "").replace("\t", "");
    }
}
