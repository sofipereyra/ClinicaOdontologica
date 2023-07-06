package com.backend.clinicaOdontologica.controller;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.dto.TurnoDto;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.ITurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
@CrossOrigin
public class TurnoController {

    private final ITurnoService turnoService;

    // esto seria  la secretaria que recibe y
    // gestiona todas las citas en el consultorio del dentista.

    @Autowired
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    // aca se puede  programar una nuevo turno .
    // ingresar   la nueva info del turno  y se guarda
    @Operation(summary = "Registrar turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El turno no se pudo registrar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @PostMapping("/registrar")
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody Turno turno) throws ResourceNotFoundException{
        TurnoDto turnoDto = turnoService.registrarTurno(turno);
        if (turnoDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoDto);  // turno programado con éxito
        } else {
            return ResponseEntity.badRequest().build();  //  mala suerte no se pudo programar  el turno .
        }
    }

    // aca  hay una lista de todos los turnos
    // para pedir y consultar
    @Operation(summary = "Listar turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de turnos encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "No se pudieron listar los turnos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @GetMapping
    public List<TurnoDto> listarTurnos() {
        return turnoService.listarTodos();
    }

    //  búsca  turno para el dentista por ID.
    // pones  el ID del turno que  y te devuelve todos los detalles.
    @Operation(summary = "Buscar turno por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turnos encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "No se pudier encontrar el turno",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), null, HttpStatus.OK);
    }

    @Operation(summary = "Editar turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno editado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El turno no se pudo editar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizarTurno(@Valid @RequestBody Turno turno) throws ResourceNotFoundException{

        return new ResponseEntity<>(turnoService.actualizarTurno(turno), null, HttpStatus.OK);
    }

    // aca tenemos un metodo para cancelar una  turno
    // Solo  tenes que ingresar  el ID de la cita que deseas cancelar,
    //@DeleteMapping("/eliminar/{
    @Operation(summary = "Eliminar turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El turno no se pudo eliminar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado");
    }
}
