package es.ieslavereda.miraveredabackend.repository;

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
        return null;
    }
    @Override
    public Integer addPelicula(Pelicula pelicula) throws SQLException {
        return null;
    }
    @Override
    public boolean updatePelicula(Pelicula pelicula) throws SQLException {
        return false;
    }
    @Override
    public Pelicula deletePelicula(int id) throws SQLException {
        return null;
    }
    @Override
    public List<Pelicula> getAllPeliculas() throws SQLException {
        return null;
    }
}
