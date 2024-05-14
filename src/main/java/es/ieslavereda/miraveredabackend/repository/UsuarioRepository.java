package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.MyDataSource;
import es.ieslavereda.miraveredabackend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository implements IUsuarioRepository {

    @Autowired
    DataSource dataSource;

    @Override
    public Usuario getUsuario(int id) throws SQLException {
        return null;
    }

    @Override
    public Integer addUsuario(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean updateUsuario(Usuario usuario) throws SQLException {
        return false;
    }

    @Override
    public Usuario deleteUsuario(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Usuario> getAllUsuarios() throws SQLException {
        return null;
    }
}
