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
