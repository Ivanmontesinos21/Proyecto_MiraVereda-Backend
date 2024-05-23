package es.ieslavereda.miraveredabackend.model;

import lombok.*;
import oracle.jdbc.internal.OracleTimestamp;
import oracle.sql.DATE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Clase de el Cliente para guardar todos sus datos
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String contrasenya;
    private String email;
    private String domicilio;
    private String codigoPostal;
    private Date fechaNacimiento;

    /**
     * Metodo que convertimos un cliente desde un ResultSet
     * @param resultSet resultset que contiene al cliente
     * @return devuelve un Cliente
     * @throws SQLException enviara una exepcion en el caso de que el resultset no contenga un usuario
     */

    public static Usuario fromResultSet(ResultSet resultSet) throws SQLException {
        return new Usuario(
            resultSet.getInt("id_cliente"),
            resultSet.getString("nombre"),
            resultSet.getString("apellidos"),
            resultSet.getString("contrasenya"),
            resultSet.getString("email"),
            resultSet.getString("domicilio"),
            resultSet.getString("codigo_postal"),
            resultSet.getDate("fecha_nacimiento")
        );
    }

    /**
     * Metodo que crea un Cliente a traves de informacion que se le pasa en la aplicacion
     * @param input informacion que le pasaremos para crear el cliente
     * @return devolvera un nuevo usuario
     */

    public static Usuario fromUsuarioInput(UsuarioInput input) {
        return new Usuario(
            input.getId(),
            input.getNombre(),
            input.getApellidos(),
            input.getContrasenya(),
            input.getEmail(),
            input.getDomicilio(),
            input.getCodigoPostal(),
            new Date(input.getFechaNacimiento() * 1000)
        );
    }

    /**
     * Metodo para añadirle la tarjeta a un Usuario
     * @param rsTarjetas resultset con la informacion de la tarjeta que necesita el usuario
     * @return devolvera la informacion del usuario que se pueda mostrar
     * @throws SQLException mandará una exepcion en el caso de que la tarjeta no sea nulo
     */

    public UsuarioOutput toUsuarioOutput(ResultSet rsTarjetas) throws SQLException {
        List<Tarjeta> tarjetas = new ArrayList<>();
        if(rsTarjetas != null) {
            while(rsTarjetas.next()) {
                tarjetas.add(Tarjeta.fromResultSet(rsTarjetas));
            }
        }
        return new UsuarioOutput(
            id,
            nombre,
            apellidos,
            email,
            fechaNacimiento.getTime() / 1000,
            domicilio,
            codigoPostal,
            tarjetas
        );
    }
}
