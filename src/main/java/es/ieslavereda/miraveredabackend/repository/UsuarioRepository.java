package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.*;
import oracle.jdbc.OracleCallableStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio donde se maneja toda la informacion del Cliente
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

@Repository
public class UsuarioRepository implements IUsuarioRepository {
    @Autowired
    DataSource dataSource;

    /**
     * Metodo que se utiliza para encontrar al cliente
     * @param id Numero entero que se pide para encontrar al cliente con ese numero
     * @return devuelve un Usuario con la informacion correspondiente
     * @throws SQLException Exepcion que se puede producir porque no haya ningun cliente con esa id
     */

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

    /**
     * Metodo que se utiliza para añadir el usuario a la base de datos
     * @param usuarioInput informacion del usuario que se pide para poder crearlo
     * @return devuelve la informacion del usuario creado
     * @throws SQLException expcion que puede ocurrir si los datos entrantes no son correctos
     * @throws EmailUsedException exepcion que puede ocurrir si ya hay un email y se intenta volver a utilizarse
     */

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

    /**
     * Metodo que sirve para actualizar la información del usuario
     * @param usuarioInput informacion del usuario para poder actualizar los campos
     * @return devuelve una comprobacion que si es true es que se ha podido cambiar la informacion de dicho usuario
     * @throws SQLException Exepcion que puede ocurrir si la informacion no es correcta
     * @throws EmailUsedException Exepcion que puede ocurrir si se intenta utilizar el mismo mail en dos usuarios
     */

    @Override
    public boolean updateUsuario(UsuarioInput usuarioInput) throws SQLException, EmailUsedException {
        Usuario usuario = Usuario.fromUsuarioInput(usuarioInput);
        String sql = "{ ? = call actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
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
            st.registerOutParameter(10, Types.BOOLEAN);
            st.execute();
            boolean emailLibre = st.getBoolean(10);
            if(!emailLibre)
                throw new EmailUsedException();
            return st.getBoolean(1);
        }
    }

    /**
     * Metodo que sirve para eliminar un usuario a traves de su id
     * @param id Numero entero que sirve para poder identificar al cliente que queremos eliminar
     * @return devuelve la informacion del usuario eliminado
     * @throws SQLException Exepcion que puede ocurrir si intentamos enviar una id de un cliente que no existe
     */

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

    /**
     * Metodo que servira para darnos todos los clientes que tenemos en nuestro servidor
     * @return Devuelve una lista de la informacion correspondiente de cada cliente
     * @throws SQLException puede dar una exepcion si no hay clientes dados de alta
     */

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

    /**
     * Metodo que sirve para el login del Cliente
     * @param email String que pasaremos para comprobar que hay algun cliente dado de alta con ese email
     * @param contrasenya String que pasaremos para comprobar la contraseña del cliente
     * @return Devolvera la informacion para que si que pueda entrar en caso de que si este dado de alta
     * @throws SQLException esta exepcion ocurrira si alguno de los datos pasados no sea correcto
     */
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

    /**
     * Metodo que sirve para el Cliente de Android para poder resetear la contraseña en el caso de que el Cliente no se acuerde de ella
     * @param credenciales las credenciales Es una clase que contiene el email y la contraseña del cliente para que puede resetear la contraseña
     * @return devuelve un booleano para que se sepa si se ha podido cambiar la contraseña del cliente
     * @throws SQLException esta exepcion se encia en caso de que intente resetear una cpntraseña con un email que no este dado de alta
     */

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
