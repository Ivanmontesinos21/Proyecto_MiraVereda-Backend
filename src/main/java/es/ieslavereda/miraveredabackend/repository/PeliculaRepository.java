package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.Genero;
import es.ieslavereda.miraveredabackend.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PeliculaRepository implements IPeliculaRepository {

    @Autowired
    DataSource dataSource;


    @Override
    public Pelicula getPelicula(int id) throws SQLException {
        String sql = "SELECT * FROM peliculas WHERE id_contenidoAudiovisual = ?";
        try (Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(sql);
        ResultSet resultSet = callableStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getObject(1, Pelicula.class);
            }

        }
        return null;
    }

    @Override
    public Pelicula addPelicula(Pelicula pelicula) throws SQLException {
        String sql = "INSERT INTO peliculas (disponible_hasta,id_contenidoAudiovisual,genero,fecha_estreno,duracion,titulo,precio,descripcion,valoracion_media,nombre_director,version_idioma,id_tarifa) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            cstmt.setString(12,pelicula.getIdtarifa());
            cstmt.execute();
        }
        return pelicula;
    }
    @Override
    public boolean updatePelicula(Pelicula pelicula) throws SQLException {
        String sql="update pelicula SET disponible_hasta=?,genero=?,fecha_estreno=?,duracion=?,titulo=?,precio=?,descripcion=?,valoracion_media=?,nombre_director=?,version_idioma=?,id_tarifa=? where id_contenidoAudiovisual=?" + pelicula.getId();
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
            cstmt.setString(12,pelicula.getIdtarifa());
            cstmt.executeUpdate();


        }
        return false;
    }
    @Override
    public Pelicula deletePelicula(int id) throws SQLException {
        return null;
    }
    @Override
    public List<Pelicula> getAllPeliculas() throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM peliculas";
        try(Connection con = dataSource.getConnection();
        CallableStatement cs=con.prepareCall(sql);
        ResultSet rs=cs.executeQuery()) {
            while (rs.next()){
                peliculas.add(Pelicula.builder().id(rs.getInt("id_contenidoAudiovisual"))
                                .disponible_hasta(rs.getDate("disponible_hasta"))
                                .genero(rs.getObject("genero", Genero.class))
                                .fechaestreno(rs.getDate("fecha_estreno"))
                                .duracion(rs.getInt("duracion"))
                                .titulo(rs.getString("titulo"))
                                .precio(rs.getDouble("precio"))
                                .descripcion(rs.getString("descripcion"))
                                .media(rs.getDouble("valoracion_media"))
                                .director(rs.getString("nombre_director"))
                                .version_idioma(rs.getString("version_idioma"))
                                .idtarifa(rs.getString("id_tarifa"))
                        .build());
            }

        }
        return peliculas;
    }
}
