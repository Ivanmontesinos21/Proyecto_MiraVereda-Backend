package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UsuarioOutput {
    int id;
    String nombre;
    String apellido;
    String email;
    long fechaNacimiento;
    String domicilio;
    String codigoPostal;
    List<Tarjeta> tarjetas;
}
