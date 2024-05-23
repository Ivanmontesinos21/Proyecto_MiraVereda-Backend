package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que se utiliza para guardar la informacion de la tarjeta del usuario para poder realizar el pago
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */



@Getter
@AllArgsConstructor
public class Tarjeta {
    Integer id;
    String numTarjeta;
    String ccv;
    long fechaCaducidad;
    String titular;

    public static Tarjeta fromResultSet(ResultSet resultSet) throws SQLException {
        return new Tarjeta(
            resultSet.getInt("id_tarjeta"),
            resultSet.getString("num_tarjeta"),
            resultSet.getString("cvv"),
            resultSet.getDate("fecha_caducidad").getTime() / 1000,
            resultSet.getString("titular")
        );
    }
}
