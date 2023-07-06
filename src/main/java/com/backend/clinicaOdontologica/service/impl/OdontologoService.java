package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.OdontologoDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final ObjectMapper objectMapper;
    private final OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(ObjectMapper objectMapper, OdontologoRepository odontologoRepository) {
        this.objectMapper = objectMapper;
        this.odontologoRepository = odontologoRepository;
    }
    @Override
    public OdontologoDto registrarOdontologo(Odontologo odontologo) {
        Odontologo odontologoDto = odontologoRepository.save(odontologo);
        LOGGER.info("Odontologo guardado: {}", JsonPrinter.toString(odontologoDto));
        return objectMapper.convertValue(odontologoDto, OdontologoDto.class);
    }
    @Override
    public List<OdontologoDto> listarOdontologos() {
        List<OdontologoDto> odontologosDtos = odontologoRepository.findAll().stream().map(o -> objectMapper.convertValue(o, OdontologoDto.class)).toList();
        LOGGER.info("Listado de todos los odontologos: {}", JsonPrinter.toString(odontologosDtos));
        return odontologosDtos;
    }
    @Override
    public OdontologoDto buscarOdontologoPorId(Long id) throws ResourceNotFoundException{
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoDto odontologoDto = null;

        if(odontologoBuscado != null){
            odontologoDto = objectMapper.convertValue(odontologoBuscado, OdontologoDto.class);
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoDto)); //JsonPrinter utils
        }else{
            LOGGER.info("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("No fue posible encontrar el odontologo");
        }
        return odontologoDto;
    }

    @Override
    public OdontologoDto actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException {
        Odontologo odontologoAActualizar = odontologoRepository.findById(odontologo.getId()).orElse(null);
        OdontologoDto odontologoDtoActualizado = null;
        if (odontologoAActualizar != null) {
            odontologoAActualizar = odontologo;
            odontologoRepository.save(odontologoAActualizar);
            odontologoDtoActualizado = objectMapper.convertValue(odontologoAActualizar, OdontologoDto.class);
            LOGGER.warn("Odontólogo actualizado: {}", JsonPrinter.toString(odontologoDtoActualizado)); //Json utils
        } else {
            LOGGER.error("No fue posible actualizar los datos ya que el odontólogo no se encuentra registrado");
            throw new ResourceNotFoundException("No fue posible encontrar el odontologo");
        }
        return odontologoDtoActualizado;
    }

    @Override
    public void eliminarOdontologoPorId(Long id) throws ResourceNotFoundException{
        if (buscarOdontologoPorId(id) != null) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el odontologo con id " + id);
            throw new ResourceNotFoundException("No se ha encontrado el odontologo con id " + id);
        }
    }
}
