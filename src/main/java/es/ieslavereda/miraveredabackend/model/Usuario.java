package es.ieslavereda.miraveredabackend.model;

import lombok.*;
import oracle.sql.DATE;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String password;
    private String email;
    private String codigopostal;
    private String domicilio;
    private Date fechaNacimiento;


}
