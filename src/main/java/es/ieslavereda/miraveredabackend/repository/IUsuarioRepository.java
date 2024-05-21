package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.*;

import java.sql.SQLException;
import java.util.List;

public interface IUsuarioRepository {
    UsuarioOutput getUsuario(int id) throws SQLException;
    UsuarioOutput addUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException;
    void updateUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException;
    UsuarioOutput deleteUsuario(int id) throws SQLException;
    List<UsuarioOutput> getAllUsuarios() throws SQLException;
    UsuarioOutput login(String email, String contrasenya) throws SQLException;
    boolean resetPass(Credenciales credenciales) throws SQLException;
}
