package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.Pelicula;

import java.sql.SQLException;
import java.util.List;

public interface IPeliculaRepository {
    Pelicula getPelicula(int id) throws SQLException;
    Integer addPelicula(Pelicula pelicula) throws SQLException;
    boolean updatePelicula(Pelicula pelicula) throws SQLException;
    Pelicula deletePelicula(int id) throws SQLException;
    List<Pelicula> getAllPeliculas() throws SQLException;
}
