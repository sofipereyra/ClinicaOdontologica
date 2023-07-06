package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.PacienteDto;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {
    PacienteDto registrarPaciente(Paciente paciente);
    List<PacienteDto> listarPacientes();
   // PacienteDto buscarPacientePorDni(String dni);
    PacienteDto buscarPacientePorId(Long id) throws ResourceNotFoundException;
    PacienteDto actualizarPaciente(Paciente paciente) throws ResourceNotFoundException;
    void eliminarPaciente(Long id) throws ResourceNotFoundException;
}
