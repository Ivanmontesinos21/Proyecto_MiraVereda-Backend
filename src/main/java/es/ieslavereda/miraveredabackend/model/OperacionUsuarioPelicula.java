package es.ieslavereda.miraveredabackend.model;

import lombok.Getter;
/**
 * Clase utilizada para realizar las operaciones que implican a un usuario y una película.
 * @Version 1.0 2024/05/24
 * @Author David,Ian,Jaime,Ivan
 */


@Getter
public class OperacionUsuarioPelicula {
    private String email;
    private String contrasenya;
    private int idPelicula;
}
