package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UsuarioOutput {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private long fechaNacimiento;
    private String domicilio;
    private String codigoPostal;
    private List<Tarjeta> tarjetas;
}
