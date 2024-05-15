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
        String sql = "SELECT * FROM Cliente WHERE id_cliente = " + id;
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement callableStatement = connection.prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            System.out.println(sql);
            if (resultSet.next()) {
                System.out.println("B");
                return resultSet.getObject(1, Usuario.class);
            }

        }
        catch(SQLException err) {
            err.printStackTrace();
        }
        System.out.println("C");
        return null;
    }

    @Override
    public Usuario addUsuario(Usuario usuario) throws SQLException {
        /*
        String sql="Insert into Cliente (id_cliente, nombre, apellido,password,email,domicilio,codigopostal,tarjeta,fechanacimiento) values (?,?,?,?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setInt(1, usuario.getId());
            callableStatement.setString(2, usuario.getNombre());
            callableStatement.setString(3, usuario.getApellidos());
            callableStatement.setString(4, usuario.getPassword());
            callableStatement.setString(5, usuario.getEmail());
            callableStatement.setString(6, usuario.getDomicilio());
            callableStatement.setString(7, usuario.getCodigopostal());
            callableStatement.setObject(8, usuario.getTarjeta());
            callableStatement.setDate(9, (Date) usuario.getFechaNacimiento());
            callableStatement.execute();
        }
        return usuario;
        */
        return null;
    }

    @Override
    public boolean updateUsuario(Usuario usuario) throws SQLException {
        /*
        String sql="Update Cliente SET nombre=?,apellidos=?,password=?,email=?,domicilio=?,codigopostal=?,tarjeta=?,fechanacimiento=? WHERE id_cliente =" + usuario.getId();
        try (Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, usuario.getNombre());
            callableStatement.setString(2, usuario.getApellidos());
            callableStatement.setString(3, usuario.getPassword());
            callableStatement.setString(4, usuario.getEmail());
            callableStatement.setString(5, usuario.getDomicilio());
            callableStatement.setString(6, usuario.getCodigopostal());
            callableStatement.setObject(7, usuario.getTarjeta());
            callableStatement.setDate(8, (Date) usuario.getFechaNacimiento());
            callableStatement.executeUpdate();
        }
        */
        return false;
    }

    @Override
    public Usuario deleteUsuario(int id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id_cliente = " + id;
        Usuario usuario = getUsuario(id);
        try (Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.execute();
        }
        return usuario;
    }

    @Override
    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
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
                                .codigopostal(rs.getString("codigo_postal"))
                                .fechaNacimiento(rs.getDate("fecha_nacimiento"))
                        .build());

            }
        }
        return usuarios;
    }
}
