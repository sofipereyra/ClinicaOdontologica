package com.backend.clinicaOdontologica.entity;

import javax.persistence.*;

import javax.validation.constraints.*;
@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 15, message = "La matrícula debe tener hasta 15 caracteres")
    @NotNull(message = "La matrícula del odontólogo no puede ser nulo")
    private String matricula;
    @Size(max = 20, message = "El nombre debe tener hasta 20 caracteres")
    @NotNull(message = "El nombre del odontólogo no puede ser nulo")
    private String nombre;
    @Size(max = 20, message = "El apellido debe tener hasta 20 caracteres")
    @NotNull(message = "El apellido del odontólogo no puede ser nulo")
    private String apellido;

    public Odontologo() {
    }

    public Odontologo(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Odontologo{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
