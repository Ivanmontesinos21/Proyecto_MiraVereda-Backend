package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface IUsuarioRepository {
    Usuario getUsuario(int id) throws SQLException;
    Integer addUsuario(Usuario usuario) throws SQLException;
    boolean updateUsuario(Usuario usuario) throws SQLException;
    Usuario deleteUsuario(int id) throws SQLException;
    List<Usuario> getAllUsuarios() throws SQLException;

}
