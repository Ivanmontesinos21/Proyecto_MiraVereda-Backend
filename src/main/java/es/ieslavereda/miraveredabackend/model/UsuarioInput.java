package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioInput {
    Integer id;
    String nombre;
    String apellidos;
    String email;
    long fechaNacimiento;
    String contrasenya;
    String domicilio;
    String codigoPostal;
}
