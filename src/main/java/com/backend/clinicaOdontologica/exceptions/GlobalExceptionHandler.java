package com.backend.clinicaOdontologica.exceptions;
import com.google.gson.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepción cuando no se encuentra el recurso solicitado
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> procesarNotFoundException(ResourceNotFoundException exception) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exceptionMessage.put("message", "Recurso no encontrado");
        exceptionMessage.put("details", exception.getMessage());
        return exceptionMessage;
    }


    // Manejo de excepción cuando ocurre un error de validación en los argumentos del método
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> procesarValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            exceptionMessage.put(fieldName, errorMessage);
        });
        return exceptionMessage;
    }

    // Manejo de excepción cuando se produce una solicitud incorrecta
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> procesarBadRequest(BadRequestException exception) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exceptionMessage.put("path", String.valueOf(exception.getClass()));
        exceptionMessage.put("message", "Solicitud incorrecta: " + exception.getMessage());
        return exceptionMessage;
    }

    // Manejo de excepción cuando se produce un error en el análisis de JSON
    @ExceptionHandler({JsonParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> procesarJsonParseException(JsonParseException exception) {
        Map<String, String> exceptionMessage = new HashMap<>();
        exceptionMessage.put("path", String.valueOf(exception.getClass()));
        exceptionMessage.put("message", "Error de análisis JSON: " + exception.getMessage());
        return exceptionMessage;
    }

}
