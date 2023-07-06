package com.backend.clinicaOdontologica.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Adaptador personalizado para la serialización y deserialización de objetos LocalDateTime.
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Deserializa un elemento JSON en un objeto LocalDateTime.
     *
     * @param json    el elemento JSON a deserializar.
     * @param typeOfT el tipo de objeto LocalDateTime.
     * @param context el contexto de deserialización JSON.
     * @return el objeto LocalDateTime deserializado.
     * @throws JsonParseException si hay un error durante la deserialización JSON.
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH));
    }

    /**
     * Serializa un objeto LocalDateTime en formato JSON.
     *
     * @param localDateTime el objeto LocalDateTime a serializar.
     * @param srcType       el tipo del objeto fuente.
     * @param context       el contexto de serialización JSON.
     * @return el elemento JSON que representa el objeto LocalDateTime.
     */
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}
