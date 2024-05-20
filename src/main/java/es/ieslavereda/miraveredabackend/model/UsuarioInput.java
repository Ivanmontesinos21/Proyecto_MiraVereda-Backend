package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioInput {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String email;
    private long fechaNacimiento;
    private String contrasenya;
    private String domicilio;
    private String codigoPostal;
}
