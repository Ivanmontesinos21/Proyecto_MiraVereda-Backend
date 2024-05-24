package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.*;
import oracle.jdbc.OracleCallableStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Repositorio donde se maneja toda la informacion correspondiente a los ContenidoAudioVisuales
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

@Repository
public class PeliculaRepository implements IPeliculaRepository {

    @Autowired
    DataSource dataSource;

    /**
     * Metodo que se utiliza para obtener una pelicula a traves de una ID
     * @param id numero que se pasa para poder identificar cual es el ContenidoAudioVisual
     * @return devuelve la informacion de la pelicula con la id correspondiente
     * @throws SQLException envia una exepcion en el caso de que el ContenidoAudioVisual no exista
     */


    @Override
    public ContenidoAudiovisualOutput getPelicula(int id) throws SQLException {
        String sql = "{ call get_pelicula(?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setInt(1, id);
            st.registerOutParameter(2, Types.REF_CURSOR);
            st.registerOutParameter(3, Types.REF_CURSOR);
            st.execute();
            ResultSet rsUsuario = ((OracleCallableStatement)st).getCursor(3);
            if(rsUsuario.next()) {
                ResultSet rsActores = ((OracleCallableStatement)st).getCursor(2);
                return new ContenidoAudiovisualOutput(ContenidoAudiovisual.fromResultSet(rsUsuario), rsActores);
            }
        }
        return null;
    }

    /**
     * Metodo para añadir ContenidoAudioVisual a la base de datos
     * @param pelicula objeto que contiene toda la informacion para poder crear el ContenidoAudioVisual
     * @return devuelve la informacion correspondiente del ContenidoAudioVisual creado
     * @throws SQLException envia una exepcion en caso de que no se respete la integridad referencial de la base de datos
     */

    @Override
    public ContenidoAudiovisualOutput addPelicula(ContenidoAudiovisualInput pelicula) throws SQLException {
        String sql = "{ call crear_pelicula(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            String tipo = pelicula.getTipo();
            st.setString(1, tipo);
            st.setString(2, pelicula.getGenero());
            st.setDate(3, new Date(pelicula.getFechaEstreno() * 1000));
            st.setInt(4, pelicula.getDuracion());
            st.setString(5, pelicula.getTitulo());
            st.setInt(6, pelicula.getPrecio());
            st.setString(7, pelicula.getDescripcion());
            st.setString(8, pelicula.getNombreDirector());
            st.setString(9, pelicula.getVersionIdioma());
            st.setInt(10, pelicula.getIdTarifa());
            st.setString(11, pelicula.getImagenUrl());
            if(pelicula.getDisponibleHasta() != null)
                st.setDate(12, new Date(pelicula.getDisponibleHasta() * 1000));
            else
                st.setNull(12, Types.DATE);
            if(pelicula.getDisponibleDesde() != null)
                st.setDate(13, new Date(pelicula.getDisponibleDesde() * 1000));
            else
                st.setNull(13, Types.DATE);
            if(pelicula.getIdSerie() != null)
                st.setInt(14, pelicula.getIdSerie());
            else
                st.setNull(14, Types.INTEGER);
            if(pelicula.getTemporada() != null)
                st.setInt(15, pelicula.getTemporada());
            else
                st.setNull(15, Types.INTEGER);
            Set<Integer> actores = pelicula.getIdActores();
            if(actores == null || actores.isEmpty())
                st.setString(16, "");
            else
                st.setString(16, actores.stream().map(id -> id.toString()).collect(Collectors.joining(",")) + ',');
            st.registerOutParameter(17, Types.REF_CURSOR);
            st.registerOutParameter(18, Types.REF_CURSOR);
            st.execute();
            ResultSet rsUsuario = ((OracleCallableStatement)st).getCursor(18);
            if(rsUsuario.next()) {
                ResultSet rsActores = ((OracleCallableStatement)st).getCursor(17);
                return new ContenidoAudiovisualOutput(ContenidoAudiovisual.fromResultSet(rsUsuario), rsActores);
            }
        }
        return null;
    }

    /**
     * Metodo que sirve para actualizar un ContenidoAudioVisual
     * @param pelicula objeto que contiene la informacion del ContenidoAudioVisual que vamos a actualizar
     * @return devuelve en caso afirmativo que se haya podido actualizar un true
     * @throws SQLException puede enviar una exepcion si la pelicula no se ha podido actualizar
     */
    @Override
    public boolean updatePelicula(ContenidoAudiovisualInput pelicula) throws SQLException {
        String sql = "{ ? = call actualizar_pelicula(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setInt(2, pelicula.getId());
            st.setString(3, pelicula.getGenero());
            st.setDate(4, new Date(pelicula.getFechaEstreno() * 1000));
            st.setInt(5, pelicula.getDuracion());
            st.setString(6, pelicula.getTitulo());
            st.setInt(7, pelicula.getPrecio());
            st.setString(8, pelicula.getDescripcion());
            st.setString(9, pelicula.getNombreDirector());
            st.setString(10, pelicula.getVersionIdioma());
            st.setInt(11, pelicula.getIdTarifa());
            st.setString(12, pelicula.getImagenUrl());
            if(pelicula.getDisponibleHasta() != null)
                st.setDate(13, new Date(pelicula.getDisponibleHasta() * 1000));
            else
                st.setNull(13, Types.DATE);
            if(pelicula.getDisponibleDesde() != null)
                st.setDate(14, new Date(pelicula.getDisponibleDesde() * 1000));
            else
                st.setNull(14, Types.DATE);
            if(pelicula.getIdSerie() != null)
                st.setInt(15, pelicula.getIdSerie());
            else
                st.setNull(15, Types.INTEGER);
            if(pelicula.getTemporada() != null)
                st.setInt(16, pelicula.getTemporada());
            else
                st.setNull(16, Types.INTEGER);
            Set<Integer> actores = pelicula.getIdActores();
            if(actores == null || actores.isEmpty())
                st.setString(17, "");
            else
                st.setString(17, actores.stream().map(id -> id.toString()).collect(Collectors.joining(",")) + ',');
            st.registerOutParameter(1, Types.BOOLEAN);
            st.execute();
            return st.getBoolean(1);
        }
    }

    /**
     * Metodo que se utiliza en el caso de que querramos eliminar un ContenidoAudioVisual
     * @param id Numero que servira para poder identificar el ContenidoAudioVisual
     * @return devuelve la informacion de la pelicula eliminada
     * @throws SQLException enviara una exepcion en el caso de que no se pueda eliminar la pelicula
     */
    @Override
    public ContenidoAudiovisualOutput deletePelicula(int id) throws SQLException {
        String sql = "DELETE FROM CONTENIDO_AUDIOVISUAL WHERE ID_CA = ?";
        ContenidoAudiovisualOutput ca = getPelicula(id);
        if(ca == null)
            return null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement st = connection.prepareCall(sql);
            st.setInt(1, id);
            st.execute();
            return ca;
        }
    }

    /**
     * Metodo que servira para que obtengamos una lista de todos los ContenidoAudioVisuales qye tengamos
     * @param afterId identificador que le pondremos a las peliculas para enseñar
     * @return devuelve una lista de ContenidoAudioVisuales que tenemos disponibles
     * @throws SQLException enviara una exepcion si no hay ningun ContenidoAudioVisual
     */

    @Override
    public List<ContenidoAudiovisualOutput> getAllPeliculas(Integer afterId) throws SQLException {
        List<ContenidoAudiovisualOutput> peliculas = new ArrayList<>();
        String sql = "{ ? = call get_peliculas(?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            if(afterId != null)
                st.setInt(2, afterId);
            else
                st.setNull(2, Types.INTEGER);
            st.registerOutParameter(1, Types.REF_CURSOR);
            st.execute();
            ResultSet rs = ((OracleCallableStatement)st).getCursor(1);
            while(rs.next()) {
                peliculas.add(new ContenidoAudiovisualOutput(ContenidoAudiovisual.fromResultSet(rs), null));
            }
        }
        return peliculas;
    }

    /**
     * Metodo que utilizamos para saber cual es el carrito del usuario
     * @param credenciales informacion que necesitamos del usuario que es el email y la contraseña
     * @return devolvera una lista de los ContenidoAudioVisuales que hayamos elegido
     * @throws SQLException enviara una exepcion en el caso de que no tengamos las credenciales
     */

    @Override
    public List<ContenidoAudiovisualOutput> getCarrito(Credenciales credenciales) throws SQLException {
        List<ContenidoAudiovisualOutput> peliculas = new ArrayList<>();
        String sql = "{ ? = call get_carrito(?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(2, credenciales.getEmail());
            st.setString(3, credenciales.getContrasenya());
            st.registerOutParameter(1, Types.REF_CURSOR);
            st.execute();
            ResultSet rs = ((OracleCallableStatement)st).getCursor(1);
            while(rs.next()) {
                peliculas.add(new ContenidoAudiovisualOutput(ContenidoAudiovisual.fromResultSet(rs), null));
            }
        }
        return peliculas;
    }

    /**
     * Metodo que servira para añadir al carrito un ContenidoAudioVisuaL
     * @param op informacion que necesita la API para poder añadir al carrito
     * @return devuelve la id del ContenidoAudioVisual para añadirlo al carrito
     * @throws SQLException enviara una exepcion en el caso de que no quede ningun ContenidoAudioVisual disponible
     */

    @Override
    public int addCarrito(OperacionUsuarioPelicula op) throws SQLException {
        String sql = "{ ? = call anyadir_carrito(?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(2, op.getEmail());
            st.setString(3, op.getContrasenya());
            st.setInt(4, op.getIdPelicula());
            st.registerOutParameter(1, Types.INTEGER);
            st.execute();
            return st.getInt(1);
        }
    }

    /**
     * Metodo que sirve para eliminar un ContenidoAudioVisual del carrito
     * @param op Objeto que tiene la informacion correspondiente para poder realizar la operacion de eliminar
     * @return devuelve la id del ContenidoAudioVisual que quieres eliminar
     * @throws SQLException enviara una exepcion en el caso de que no se pueda realizar la operacion
     */

    @Override
    public int deleteCarrito(OperacionUsuarioPelicula op) throws SQLException {
        String sql = "{ ? = call quitar_carrito(?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(2, op.getEmail());
            st.setString(3, op.getContrasenya());
            st.setInt(4, op.getIdPelicula());
            st.registerOutParameter(1, Types.INTEGER);
            st.execute();
            return st.getInt(1);
        }
    }

    @Override
    public int pagar(Credenciales credenciales) throws SQLException {
        String sql = "{ ? = call pagar(?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(2, credenciales.getEmail());
            st.setString(3, credenciales.getContrasenya());
            st.registerOutParameter(1, Types.INTEGER);
            st.execute();
            return st.getInt(1);
        }
    }

    @Override
    public void addActor(Actor actor) throws SQLException {
        String sql = "INSERT INTO ACTOR VALUES (?, ?, ?, (SELECT CURRENT_TIMESTAMP FROM DUAL))";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement st = connection.prepareCall(sql);
            st.setString(1, actor.getDni());
            st.setString(2, actor.getNombre());
            st.setString(3, actor.getApellidos());
            st.execute();
        }
    }

    @Override
    public boolean estaAlquilada(OperacionUsuarioPelicula op) throws SQLException {
        String sql = "{ ? = call esta_alquilada(?, ?, ?) }";
        try(Connection connection = dataSource.getConnection()) {
            CallableStatement st = connection.prepareCall(sql);
            st.setString(2, op.getEmail());
            st.setString(3, op.getContrasenya());
            st.setInt(4, op.getIdPelicula());
            st.registerOutParameter(1, Types.BOOLEAN);
            st.execute();
            return st.getBoolean(1);
        }
    }
}
