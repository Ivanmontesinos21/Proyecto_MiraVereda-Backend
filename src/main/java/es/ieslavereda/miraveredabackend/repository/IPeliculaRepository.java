package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.PeliculaInput;
import es.ieslavereda.miraveredabackend.model.ContenidoAudiovisualOutput;

import java.sql.SQLException;
import java.util.List;

public interface IPeliculaRepository {
    ContenidoAudiovisualOutput getPelicula(int id) throws SQLException;
    Integer addPelicula(PeliculaInput pelicula) throws SQLException;
    boolean updatePelicula(PeliculaInput pelicula) throws SQLException;
    ContenidoAudiovisualOutput deletePelicula(int id) throws SQLException;
    List<ContenidoAudiovisualOutput> getAllPeliculas() throws SQLException;
}
