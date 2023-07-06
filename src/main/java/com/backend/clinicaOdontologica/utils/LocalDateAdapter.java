package com.backend.clinicaOdontologica.utils;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Adaptador personalizado para la serialización y deserialización de objetos LocalDate.
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Serializa un objeto LocalDate en formato JSON.
     *
     * @param localDate el objeto LocalDate a serializar.
     * @param srcType   el tipo del objeto fuente.
     * @param context   el contexto de serialización JSON.
     * @return el elemento JSON que representa el objeto LocalDate.
     */
    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(dateFormatter.format(localDate));
    }

    /**
     * Deserializa un elemento JSON en un objeto LocalDate.
     *
     * @param json       el elemento JSON a deserializar.
     * @param typeOfT    el tipo de objeto LocalDate.
     * @param context    el contexto de deserialización JSON.
     * @return el objeto LocalDate deserializado.
     * @throws JsonParseException si hay un error durante la deserialización JSON.
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
    }
}
