package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.DomicilioDto;
import com.backend.clinicaOdontologica.dto.PacienteDto;
import com.backend.clinicaOdontologica.entity.Domicilio;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.PacienteRepository;
import com.backend.clinicaOdontologica.service.IPacienteService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacienteService implements IPacienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final ObjectMapper objectMapper;
    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(ObjectMapper objectMapper, PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
        this.objectMapper = objectMapper;
    }
    @Override
    public PacienteDto registrarPaciente(Paciente paciente) {
        Paciente pacienteNuevo = pacienteRepository.save(paciente);
        DomicilioDto domicilioDto = objectMapper.convertValue(pacienteNuevo.getDomicilio(), DomicilioDto.class);
        PacienteDto pacienteDtoNuevo = objectMapper.convertValue(pacienteNuevo, PacienteDto.class);
        pacienteDtoNuevo.setDomicilioDto(domicilioDto);

        LOGGER.info("Nuevo paciente registrado con exito: {}",  JsonPrinter.toString(pacienteDtoNuevo));

        return pacienteDtoNuevo;
    }

    @Override
    public List<PacienteDto> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDto> pacienteDtos = pacientes.stream()
                .map(paciente -> {
                    Domicilio domicilio = paciente.getDomicilio();
                    DomicilioDto domicilioDto = objectMapper.convertValue(domicilio, DomicilioDto.class);
                    return new PacienteDto(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getFechaIngreso(), domicilioDto);
                })
                .toList();

        LOGGER.info("Lista de todos los pacientes: {}", JsonPrinter.toString(pacienteDtos));
        return pacienteDtos;
    }

    /*@Override
    public Paciente buscarPacientePorDni(String dni) {
        return objectMapper.convertValue(pacienteIDao.buscarPorDni(dni), PacienteDto.class);
    }*/

    @Override
    public PacienteDto buscarPacientePorId(Long id)  throws ResourceNotFoundException{
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteDto pacienteDto = null;
        if (pacienteBuscado != null) {
            DomicilioDto domicilioDto = objectMapper.convertValue(pacienteBuscado.getDomicilio(), DomicilioDto.class);
            pacienteDto = objectMapper.convertValue(pacienteBuscado, PacienteDto.class);
            pacienteDto.setDomicilioDto(domicilioDto);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteDto));

        } else {
            LOGGER.info("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("No fue posible encontrar el paciente");
        }

        return pacienteDto;
    }

    @Override
    public PacienteDto actualizarPaciente(Paciente paciente) throws ResourceNotFoundException{
        Paciente pacienteAActualizar = pacienteRepository.findById(paciente.getId()).orElse(null);
        PacienteDto pacienteActualizadoDto = null;

        if (pacienteAActualizar != null) {
            pacienteAActualizar = paciente;
            pacienteRepository.save(pacienteAActualizar);

            DomicilioDto domicilioDto = objectMapper.convertValue(pacienteAActualizar.getDomicilio(), DomicilioDto.class);
            pacienteActualizadoDto = objectMapper.convertValue(pacienteAActualizar, PacienteDto.class);
            pacienteActualizadoDto.setDomicilioDto(domicilioDto);
            LOGGER.info("Paciente actualizado con exito: {}",  JsonPrinter.toString(pacienteActualizadoDto));
        } else {
            LOGGER.error("No fue posible actualizar los datos ya que el paciente no se encuentra registrado");
            throw new ResourceNotFoundException("No fue posible actualizar los datos ya que el paciente no se encuentra registrado");
        }

        return pacienteActualizadoDto;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        if(buscarPacientePorId(id) != null){
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            LOGGER.error("No se ha encontrado el paciente con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id " + id);
        }
    }
}
