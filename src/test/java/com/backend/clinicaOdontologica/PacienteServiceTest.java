package com.backend.clinicaOdontologica;

import com.backend.clinicaOdontologica.dto.PacienteDto;
import com.backend.clinicaOdontologica.entity.Domicilio;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.IPacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteServiceTest {
    @Autowired
    private IPacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarUnPaciente(){
        Paciente paciente = new Paciente("Sofi", "Pereyra", "123456", LocalDate.now(), new Domicilio("Barca", 204L, "Ciudad", "Mendoza"));
        PacienteDto pacienteResult = pacienteService.registrarPaciente(paciente);

        assertEquals(1, paciente.getId());
    }

    @Test
    @Order(2)
    void deberiaListarSolamenteUnPaciente() {
        List<PacienteDto> pacienteDtoList = pacienteService.listarPacientes();

        assertEquals(1, pacienteDtoList.size());
    }

    @Test
    @Order(3)
    void deberiaEliminarElPacienteConId1() throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(1L));
    }
}
