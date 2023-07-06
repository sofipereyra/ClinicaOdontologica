package com.backend.clinicaOdontologica.entity;
import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
@Table(name = "DOMICILIOS")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 40, message = "La calle debe tener hasta 40 caracteres")
    @NotNull(message = "La calle del domicilio no puede ser nulo")
    private String calle;
    @NotNull(message = "El numero del domicilio no puede ser nulo")
    private Long numero;
    @Size(max = 20, message = "La localidad debe tener hasta 20 caracteres")
    @NotNull(message = "La localidad del domicilio no puede ser nulo")
    private String localidad;
    @Size(max = 20, message = "La provincia debe tener hasta 20 caracteres")
    @NotNull(message = "La provincia del domicilio no puede ser nulo")
    private String provincia;

    public Domicilio() {
    }

    public Domicilio(String calle, Long numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Long getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
