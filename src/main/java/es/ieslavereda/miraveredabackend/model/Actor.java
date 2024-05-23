package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase del Actor
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private String dni;
    private String nombre;
    private String apellidos;

    public static Actor fromResultSet(ResultSet resultSet) throws SQLException {
        return new Actor(
            resultSet.getString("dni"),
            resultSet.getString("nombre"),
            resultSet.getString("apellidos")
        );
    }
}
