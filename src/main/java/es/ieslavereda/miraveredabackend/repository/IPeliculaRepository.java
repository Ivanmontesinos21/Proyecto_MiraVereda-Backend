package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.ContenidoAudiovisualInput;
import es.ieslavereda.miraveredabackend.model.ContenidoAudiovisualOutput;

import java.sql.SQLException;
import java.util.List;

public interface IPeliculaRepository {
    ContenidoAudiovisualOutput getPelicula(int id) throws SQLException;
    ContenidoAudiovisualOutput addPelicula(ContenidoAudiovisualInput pelicula) throws SQLException;
    boolean updatePelicula(ContenidoAudiovisualInput pelicula) throws SQLException;
    ContenidoAudiovisualOutput deletePelicula(int id) throws SQLException;
    List<ContenidoAudiovisualOutput> getAllPeliculas(Integer afterId) throws SQLException;
}
