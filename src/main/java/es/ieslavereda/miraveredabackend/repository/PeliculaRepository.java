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

@Repository
public class PeliculaRepository implements IPeliculaRepository {

    @Autowired
    DataSource dataSource;


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

    @Override
    public int addCarrito(OperacionCarrito op) throws SQLException {
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

    @Override
    public int deleteCarrito(OperacionCarrito op) throws SQLException {
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
            System.out.println(1);
            CallableStatement st = connection.prepareCall(sql);
            System.out.println(2);
            st.setString(2, credenciales.getEmail());
            st.setString(3, credenciales.getContrasenya());
            st.registerOutParameter(1, Types.INTEGER);
            System.out.println(3);
            st.execute();
            System.out.println(4);
            return st.getInt(1);
        }
    }
}
