package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.dto.PacienteDto;
import com.backend.clinicaOdontologica.dto.TurnoDto;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.TurnoRepository;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService){
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public static void deleteTurno(int turnoId) {
    }

    @Override
    public TurnoDto registrarTurno(Turno turno)  throws ResourceNotFoundException {
        TurnoDto turnoDto = null;
        PacienteDto paciente = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        OdontologoDto odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if(paciente == null || odontologo == null) {
            if(paciente == null && odontologo == null) {
                LOGGER.error("El paciente y el odontologo no se encuentran en nuestra base de datos");
                throw new ResourceNotFoundException("El paciente y el odontologo no se encuentran en nuestra base de datos");
            }
            else if (paciente == null){
                LOGGER.error("El paciente no se encuentra en nuestra base de datos");
                throw new ResourceNotFoundException("El paciente no se encuentra en nuestra base de datos");
            } else {
                LOGGER.error("El odontologo no se encuentra en nuestra base de datos");
                throw new ResourceNotFoundException("El odontologo no se encuentra en nuestra base de datos");
            }

        } else {
            turnoDto = TurnoDto.fromTurno(turnoRepository.save(turno));
            LOGGER.info("Nuevo turno registrado con exito: {}", JsonPrinter.toString(turnoDto));
        }

        return turnoDto;
    }

    @Override
    public List<TurnoDto> listarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDto> turnoDtoList = turnos.stream()
                .map(TurnoDto::fromTurno)
                .toList();

        LOGGER.info("Lista de todos los turnos: {}",JsonPrinter.toString(turnoDtoList));
        return turnoDtoList;
    }

    @Override
    public TurnoDto buscarTurnoPorId(Long id) throws ResourceNotFoundException {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoDto turnoDto = null;
        if (turnoBuscado != null) {
            turnoDto = TurnoDto.fromTurno(turnoBuscado);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoDto));
        } else {
            LOGGER.info("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("EL turno no se encuentra registado en la base de datos.");
        }
        return turnoDto;
    }

    @Override
    public TurnoDto actualizarTurno(Turno turno) throws ResourceNotFoundException {
        Turno turnoAActualizar = turnoRepository.findById(turno.getId()).orElse(null);
        TurnoDto turnoDtoActualizado = null;
        if (turnoAActualizar != null) {
            turnoAActualizar = turno;
            turnoRepository.save(turnoAActualizar);
            turnoDtoActualizado = TurnoDto.fromTurno(turnoAActualizar);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoDtoActualizado));
        } else {
            LOGGER.error("No fue posible actualizar los datos ya que el turno no se encuentra registrado");
            throw new ResourceNotFoundException("No se pudo actualizar, el turno no se encuentra registrado.");
        }

        return turnoDtoActualizado;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if(buscarTurnoPorId(id) != null){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con id "+ id);
        }
    }
}
