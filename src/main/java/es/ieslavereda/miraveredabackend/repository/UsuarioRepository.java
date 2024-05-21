package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.*;
import oracle.jdbc.OracleCallableStatement;
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
    public UsuarioOutput getUsuario(int id) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement st = connection.prepareCall(sql);
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if(resultSet.next()) {
                return Usuario.fromResultSet(resultSet).toUsuarioOutput(null);
            }
        }
        return null;
    }

    @Override
    public UsuarioOutput addUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException {
        Usuario usuario = Usuario.fromUsuarioInput(usuarioInput);
        String sql = "{ ? = call crear_usuario(?, ?, ?, ?, ?, ?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(2, usuario.getNombre());
            st.setString(3, usuario.getApellidos());
            st.setString(4, usuario.getContrasenya());
            st.setString(5, usuario.getEmail());
            st.setString(6, usuario.getDomicilio());
            st.setString(7, usuario.getCodigoPostal());
            st.setDate(8, usuario.getFechaNacimiento());
            st.registerOutParameter(1, Types.BOOLEAN);
            st.registerOutParameter(9, Types.REF_CURSOR);
            st.execute();
            boolean emailLibre = st.getBoolean(1);
            if(!emailLibre)
                throw new EmailUsedException();
            ResultSet rs = ((OracleCallableStatement) st).getCursor(9);
            if (rs.next()) {
                return Usuario.fromResultSet(rs).toUsuarioOutput(null);
            }
        }
        return null;
    }

    @Override
    public void updateUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException {
        Usuario usuario = Usuario.fromUsuarioInput(usuarioInput);
        String sql = "{ ? = call actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setInt(2, usuario.getId());
            st.setString(3, usuario.getNombre());
            st.setString(4, usuario.getApellidos());
            st.setString(5, usuario.getContrasenya());
            st.setString(6, usuario.getEmail());
            st.setString(7, usuario.getDomicilio());
            st.setString(8, usuario.getCodigoPostal());
            st.setDate(9, usuario.getFechaNacimiento());
            st.registerOutParameter(1, Types.BOOLEAN);
            st.execute();
            boolean emailLibre = st.getBoolean(1);
            if(!emailLibre)
                throw new EmailUsedException();
        }
    }

    @Override
    public UsuarioOutput deleteUsuario(int id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        UsuarioOutput usuario = getUsuario(id);
        if(usuario == null)
            return null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement st = connection.prepareCall(sql);
            st.setInt(1, id);
            st.execute();
            return usuario;
        }
    }

    @Override
    public List<UsuarioOutput> getAllUsuarios() throws SQLException {
        String sql = "SELECT * FROM Cliente";
        try(Connection connection = dataSource.getConnection()) {
            List<UsuarioOutput> usuarios = new ArrayList<>();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            while(resultSet.next()) {
                usuarios.add(Usuario.fromResultSet(resultSet).toUsuarioOutput(null));
            }
            return usuarios;
        }
    }

    @Override
    public UsuarioOutput login(String email, String contrasenya) throws SQLException {
        String sql = "{ call login(?, ?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(1, email);
            st.setString(2, contrasenya);
            st.registerOutParameter(3, Types.REF_CURSOR);
            st.registerOutParameter(4, Types.REF_CURSOR);
            st.execute();
            ResultSet rsUsuario = ((OracleCallableStatement)st).getCursor(4);
            if(rsUsuario.next()) {
                ResultSet rsTarjetas = ((OracleCallableStatement)st).getCursor(3);
                return Usuario.fromResultSet(rsUsuario).toUsuarioOutput(rsTarjetas);
            }
        }
        return null;
    }

    @Override
    public boolean resetPass(Credenciales credenciales) throws SQLException {
        String sql = "UPDATE CLIENTE SET contrasenya = ? WHERE email = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement st = connection.prepareCall(sql);
            st.setString(1, credenciales.getContrasenya());
            st.setString(2, credenciales.getEmail());
            st.execute();
            return st.getUpdateCount() == 1;
        }
    }
}
