package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.PeliculaInput;
import es.ieslavereda.miraveredabackend.model.PeliculaOutput;

import java.sql.SQLException;
import java.util.List;

public interface IPeliculaRepository {
    PeliculaOutput getPelicula(int id) throws SQLException;
    Integer addPelicula(PeliculaInput pelicula) throws SQLException;
    boolean updatePelicula(PeliculaInput pelicula) throws SQLException;
    PeliculaOutput deletePelicula(int id) throws SQLException;
    List<PeliculaOutput> getAllPeliculas() throws SQLException;
}
