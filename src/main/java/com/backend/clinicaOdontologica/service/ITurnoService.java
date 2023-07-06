package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.TurnoDto;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {
    TurnoDto registrarTurno(Turno turno) throws ResourceNotFoundException;
    List<TurnoDto> listarTodos();
    TurnoDto buscarTurnoPorId(Long id) throws ResourceNotFoundException;
    TurnoDto actualizarTurno(Turno turno) throws ResourceNotFoundException;
    void eliminarTurno(Long id) throws ResourceNotFoundException;

}
