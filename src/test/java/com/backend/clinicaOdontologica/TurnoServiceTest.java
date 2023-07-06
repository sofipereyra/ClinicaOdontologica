package com.backend.clinicaOdontologica;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.dto.PacienteDto;
import com.backend.clinicaOdontologica.dto.TurnoDto;
import com.backend.clinicaOdontologica.entity.Domicilio;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exceptions.BadRequestException;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import com.backend.clinicaOdontologica.service.IPacienteService;
import com.backend.clinicaOdontologica.service.ITurnoService;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoServiceTest {
    @Autowired
    private ITurnoService turnoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;
    private static Paciente paciente;
    private static Odontologo odontologo;

    @BeforeAll
    public static void init(){
        paciente = new Paciente("Sofi", "Pereyra", "21336546", LocalDate.of(2023, 06, 30), new Domicilio("Lobo", 1L, "Ciudad", "Mendoza"));
        odontologo = new Odontologo("56546456", "Luna", "Lopez");
    }


    @Test
    @Order(1)
    void deberiaAgregarUnTurno() throws BadRequestException, ResourceNotFoundException {

        PacienteDto pacienteDto = pacienteService.registrarPaciente(paciente);
        OdontologoDto odontologoDto = odontologoService.registrarOdontologo(odontologo);

        TurnoDto turnoDto = turnoService.registrarTurno(new Turno(paciente, odontologo, LocalDateTime.of(2023, 8, 25, 13, 30, 00)));


        Assertions.assertNotNull(turnoDto);
        Assertions.assertNotNull(turnoDto.getId());
        Assertions.assertEquals(1, turnoDto.getId());


    }
    @Test
    @Order(2)
    void deberiaBuscarUnTurnoPorId() throws ResourceNotFoundException {
        TurnoDto turno = turnoService.buscarTurnoPorId(1L);
        Assertions.assertNotNull(turno);
    }

    @Test
    @Order(3)
    void listarTodosLosTurnos() {
        List<TurnoDto> turnoListTest = turnoService.listarTodos();
        Assertions.assertFalse(turnoListTest.isEmpty());

    }

    @Test
    @Order(4)
    void deberiaEliminarElTurnoConId1() throws ResourceNotFoundException {
        turnoService.eliminarTurno(1L);
        assertThrows(ResourceNotFoundException.class, () -> {
            turnoService.buscarTurnoPorId(1L);
        });
    }
}
