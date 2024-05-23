package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz creada para darle respuesta a los metodos que necesitemos para poder realizar todas las acciones correspondientes con los Clientes
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

public interface IUsuarioRepository {
    UsuarioOutput getUsuario(int id) throws SQLException;
    UsuarioOutput addUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException;
    boolean updateUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException;
    UsuarioOutput deleteUsuario(int id) throws SQLException;
    List<UsuarioOutput> getAllUsuarios() throws SQLException;
    UsuarioOutput login(String email, String contrasenya) throws SQLException;
    boolean resetPass(Credenciales credenciales) throws SQLException;
}
