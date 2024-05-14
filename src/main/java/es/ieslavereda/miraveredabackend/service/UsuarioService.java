package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.Usuario;
import es.ieslavereda.miraveredabackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Usuario getUsuario(int id) throws SQLException {
        return repository.getUsuario(id);
    }

    public Integer addUsuario(Usuario usuario) throws SQLException {
        return repository.addUsuario(usuario);
    }

    public boolean updateUsuario(Usuario usuario) throws SQLException{
     return repository.updateUsuario(usuario);
    }
    public Usuario deleteUsuario(int id) throws SQLException {
        return repository.deleteUsuario(id);
    }
    public List<Usuario> getAllUsuarios() throws SQLException {
        return repository.getAllUsuarios();
    }

}
