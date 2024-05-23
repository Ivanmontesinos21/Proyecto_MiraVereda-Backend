package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Clase del cliente que guardara la informacion que si podamos enseñarle
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


@Getter
@AllArgsConstructor
public class UsuarioOutput {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private long fechaNacimiento;
    private String domicilio;
    private String codigoPostal;
    private List<Tarjeta> tarjetas;
}
