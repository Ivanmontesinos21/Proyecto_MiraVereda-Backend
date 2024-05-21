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
            st.setNull(12, Types.DATE);
            st.setNull(13, Types.DATE);
            st.setNull(14, Types.INTEGER);
            st.setNull(15, Types.INTEGER);
            if(tipo.equals("pelicula"))
                st.setDate(12, new Date(pelicula.getDisponibleHasta() * 1000));
            else if(tipo.equals("capitulo")) {
                st.setDate(13, new Date(pelicula.getDisponibleDesde() * 1000));
                st.setInt(14, pelicula.getIdSerie());
                st.setInt(15, pelicula.getTemporada());
            }
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
        /*
        String sql="update pelicula SET disponible_hasta=?,genero=?,fecha_estreno=?,duracion=?,titulo=?,precio=?,descripcion=?,valoracion_media=?,nombre_director=?,version_idioma=? where id_contenidoAudiovisual=?" + pelicula.getId();
        try (Connection conn = dataSource.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setDate(1, (Date) pelicula.getDisponible_hasta());
            cstmt.setInt(2,pelicula.getId());
            cstmt.setObject(3,pelicula.getGenero());
            cstmt.setDate(4,(Date) pelicula.getFechaestreno());
            cstmt.setInt(5,pelicula.getDuracion());
            cstmt.setString(6,pelicula.getTitulo());
            cstmt.setDouble(7,pelicula.getPrecio());
            cstmt.setString(8,pelicula.getDescripcion());
            cstmt.setDouble(9,pelicula.getMedia());
            cstmt.setString(10,pelicula.getDirector());
            cstmt.setString(11, pelicula.getVersion_idioma());
            cstmt.executeUpdate();


        }
        */
        return false;
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
}
