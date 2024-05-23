package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * Clase cliente que guardaremos la informacion que nos venga desde la aplicacion
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */




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
