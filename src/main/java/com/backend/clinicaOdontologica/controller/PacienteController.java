package com.backend.clinicaOdontologica.controller;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.dto.PacienteDto;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.IPacienteService;
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
@RequestMapping("/pacientes")
@CrossOrigin
public class PacienteController {

    private final IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // administra  todas las solicitudes relacionadas con entidades de Paciente.

    /*@GetMapping("/index")
    public String buscarPacientePorDni(Model model, @RequestParam("dni") String dni){
        //  busca un Paciente según su DNI (número de identificación nacional).
        Paciente paciente = pacienteService.buscarPacientePorDni(dni);

        // Agrega los atributos del objeto Paciente al modelo, que se mostrarán en la vista.
        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());

        // devolve el nombre de la vista.
        return "index";
    }*/

    //endpoints

    // POST /pacientes/registrar
    @Operation(summary = "Registrar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El paciente no se pudo registrar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @PostMapping("/registrar")
    public ResponseEntity<PacienteDto> registrarPaciente(@RequestBody Paciente paciente) {
        // nos permite registrar un nuevo Paciente.
        // devuelve el objeto Paciente en el cuerpo de la solicitud.

        // Invocamos pacienteService para guardar el paciente en la base de datos y devolver el objeto Paciente guardado.
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.registrarPaciente(paciente);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    // GET /pacientes / pedir lista de todos los pacientes
    @Operation(summary = "Listar pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pacientes encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "No se pudo listar pacientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @GetMapping
    public List<PacienteDto> listarTodos() {
        // devuelve una lista de todos los Pacientes en la base de datos.
        // tenemos que  invocar a  pacienteService para obtener la lista de pacientes.
        return pacienteService.listarPacientes();
    }

    // GET /pacientes/{id}
    @Operation(summary = "Bucar paciente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El paciente no encontro",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPacientePorId(@PathVariable Long id)  throws ResourceNotFoundException{
        // Este punto final nos permite buscar un Paciente según su ID.
        // El ID se especifica en la URL como una variable de ruta.

        // Invocamos pacienteService para buscar el paciente por su ID y devolver el objeto Paciente.
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.buscarPacientePorId(id);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    // PUT /pacientes/actualizar
    @Operation(summary = "Editar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente editado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El paciente no se pudo editar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizarPaciente(@RequestBody Paciente paciente)  throws ResourceNotFoundException {
        //  nos permite actualizar un Paciente existente.
        //  devuelve el objeto Paciente actualizado en el cuerpo de la solicitud.

        // Invocamos pacienteService para actualizar el paciente en la base de datos y devolver el objeto Paciente actualizado.
        ResponseEntity<PacienteDto> respuesta;
        PacienteDto pacienteDto = pacienteService.actualizarPaciente(paciente);
        if (pacienteDto != null) respuesta = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return respuesta;
    }

    // DELETE /pacientes/eliminar/{id}
    @Operation(summary = "Eliminar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "El paciente no se pudo eliminar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error de servidor inesperado", content = @Content)})
    @DeleteMapping("/eliminar/{id}")
    public void eliminarPaciente(@PathVariable Long id)  throws ResourceNotFoundException{
        // nos permite eliminar un Paciente según su ID.
        // El ID se especifica en la URL como una variable de ruta.

        // tenemos que invocar pacienteService para eliminar el paciente de la base de datos.
        pacienteService.eliminarPaciente(id);
    }
}
