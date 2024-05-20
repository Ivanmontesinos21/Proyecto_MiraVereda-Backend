package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.EmailUsedException;
import es.ieslavereda.miraveredabackend.model.Usuario;
import es.ieslavereda.miraveredabackend.model.UsuarioInput;
import es.ieslavereda.miraveredabackend.model.UsuarioOutput;
import es.ieslavereda.miraveredabackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public UsuarioOutput getUsuario(int id) throws SQLException {
        return repository.getUsuario(id);
    }

    public UsuarioOutput addUsuario(UsuarioInput usuario) throws SQLException, EmailUsedException {
        return repository.addUsuario(usuario);
    }

    public void updateUsuario(UsuarioInput usuario) throws SQLException, EmailUsedException {
        repository.updateUsuario(usuario);
    }
    public UsuarioOutput deleteUsuario(int id) throws SQLException {
        return repository.deleteUsuario(id);
    }
    public List<UsuarioOutput> getAllUsuarios() throws SQLException {
        return repository.getAllUsuarios();
    }

    public UsuarioOutput login(String email, String contrasenya) throws SQLException {
        return repository.login(email, contrasenya);
    }
}
