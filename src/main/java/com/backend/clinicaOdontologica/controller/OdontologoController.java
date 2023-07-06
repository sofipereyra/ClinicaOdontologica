package com.backend.clinicaOdontologica.controller;
import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/odontologos")
@CrossOrigin
public class OdontologoController {
    private final IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    // se administran las solicitudes entrantes  relacionadas con los odontólogos.


    // Registrar un nuevo odontólogo
    @Operation(summary = "Registrar odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El odontologos no se pudo registrar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @PostMapping("/registrar")
    public ResponseEntity<OdontologoDto> registrarOdontologo(@RequestBody Odontologo odontologo) {
        //  nos permite registrar un nuevo odontólogo.
        // nos devuelve el objeto del odontólogo en el cuerpo de la solicitud.

        // Invocamos odontologoService para guardar al odontólogo en la base de datos y devolvemos el objeto del odontólogo guardado.
        return new ResponseEntity<>(odontologoService.registrarOdontologo(odontologo), null, HttpStatus.CREATED);
    }

    //  se obtienen todos los odontólogos
    @Operation(summary = "Listado de todos los odontologos")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Listado correcto",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = OdontologoDto.class))}),
    @ApiResponse(responseCode = "404", description = "Odontologos no encontrados",
            content = @Content),
    @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @GetMapping()
    public List<OdontologoDto> listarOdontologos() {
        // Este punto final devuelve una lista de todos los odontólogos en la base de datos.
        // invocamos odontologoService para obtener la lista de odontólogos.
        return odontologoService.listarOdontologos();
    }

    // Obtener un odontólogo por su ID
    @Operation(summary = "Buscar odontólogo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Odontologos no encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDto> buscarOdontologoPorId(@PathVariable Long id) throws ResourceNotFoundException{
        // Este punto nos permite buscar un odontólogo por su ID.
        // El ID se especifica en la URL como una variable de ruta.

        // Invocamos odontologoService para buscar al odontólogo por su ID y devolvemos el objeto del odontólogo.
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), null , HttpStatus.OK);
    }
    // Actualizar un odontólogo existente
    @Operation(summary = "Editar odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo editado con exito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El odontologos no se pudo editar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @PutMapping("/actualizar")
    public ResponseEntity<OdontologoDto> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException{
        // nos permite actualizar un odontólogo existente.
        // vuelve el objeto del odontólogo actualizado en el cuerpo de la solicitud.

        // Invocamos odontologoService para actualizar al odontólogo en la base de datos y devolvemos el objeto del odontólogo actualizado.
        return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologo), null, HttpStatus.OK);
    }

    // Eliminar un odontólogo por su ID
    @Operation(summary = "Eliminar odontólogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Odontologos no encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologoPorId(@PathVariable Long id)  throws ResourceNotFoundException {
        //  nos permite eliminar un odontólogo por su ID.
        // El ID se especifica en la URL como una variable de ruta.

        // Invocamos odontologoService para eliminar al odontólogo de la base de datos.
        odontologoService.eliminarOdontologoPorId(id);
        return ResponseEntity.ok("Odontologo eliminado");
    }
}
