package com.backend.clinicaOdontologica;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {
    @Autowired
     private IOdontologoService odontologoService;
    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologo(){
        Odontologo odontologo = new Odontologo("331134", "Sofi", "Pereyra");
        OdontologoDto odontologoResult = odontologoService.registrarOdontologo(odontologo);

        assertEquals("331134", odontologoResult.getMatricula());
    }

    @Test
    @Order(2)
    void deberiaListarSolamenteUnOdontologo() {
        List<OdontologoDto> odontologoDtoList = odontologoService.listarOdontologos();

        assertEquals(1, odontologoDtoList.size());
    }

    @Test
    @Order(3)
    void deberiaEliminarOdontologoConId1() throws ResourceNotFoundException {
        odontologoService.eliminarOdontologoPorId(1L);

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologoPorId(1L));
}
}
