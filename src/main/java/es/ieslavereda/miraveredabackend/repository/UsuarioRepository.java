package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.MyDataSource;
import es.ieslavereda.miraveredabackend.model.Pelicula;
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
        String sql = "SELECT id_cliente FROM Cliente WHERE id_cliente = " + id;
        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);
             ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getObject(1, Usuario.class);
            }

        }
        return null;
    }

    @Override
    public Usuario addUsuario(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean updateUsuario(Usuario usuario) throws SQLException {
        return false;
    }

    @Override
    public Usuario deleteUsuario(int id) throws SQLException {
        Usuario usuario = getUsuario(id);

    }

    @Override
    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                usuarios.add(Usuario.builder()
                                .id(rs.getInt("id_cliente"))
                                .nombre(rs.getString("nombre"))
                                .apellidos(rs.getString("apellidos"))
                                .password(rs.getString("contraseña"))
                                .email(rs.getString("email"))
                                .domicilio(rs.getString("domicilio"))
                                .codigopostal(rs.getString("codigopostal"))
                                .fechaNacimiento(rs.getDate("fecha_nacimiento"))
                        .build());

            }
        }
        return usuarios;
    }
}
