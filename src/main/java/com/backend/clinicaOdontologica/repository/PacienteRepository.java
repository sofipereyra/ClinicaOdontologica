package com.backend.clinicaOdontologica.repository;

import com.backend.clinicaOdontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Paciente que extiende JpaRepository(Java Persistence API [Application Programming Interface] Repository.)
 * Proporciona métodos predefinidos para operaciones de persistencia relacionadas con Paciente.
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
