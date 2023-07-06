package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    OdontologoDto registrarOdontologo(Odontologo odontologo);
    List<OdontologoDto> listarOdontologos();
    OdontologoDto buscarOdontologoPorId(Long id) throws ResourceNotFoundException;
    OdontologoDto actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException;
    void eliminarOdontologoPorId(Long id) throws ResourceNotFoundException;

}
