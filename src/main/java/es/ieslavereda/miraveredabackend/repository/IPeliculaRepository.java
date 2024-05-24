package es.ieslavereda.miraveredabackend.repository;

import es.ieslavereda.miraveredabackend.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz creada para poder realizar todas las acciones correspondientes con los ContenidosAudioVisuales
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

public interface IPeliculaRepository {
    ContenidoAudiovisualOutput getPelicula(int id) throws SQLException;
    ContenidoAudiovisualOutput addPelicula(ContenidoAudiovisualInput pelicula) throws SQLException;
    boolean updatePelicula(ContenidoAudiovisualInput pelicula) throws SQLException;
    ContenidoAudiovisualOutput deletePelicula(int id) throws SQLException;
    List<ContenidoAudiovisualOutput> getAllPeliculas(Integer afterId) throws SQLException;
    List<ContenidoAudiovisualOutput> getCarrito(Credenciales credenciales) throws SQLException;
    int addCarrito(OperacionUsuarioPelicula op) throws SQLException;
    int deleteCarrito(OperacionUsuarioPelicula op) throws SQLException;
    int pagar(Credenciales credenciales) throws SQLException;
    void addActor(Actor actor) throws SQLException;
    boolean estaAlquilada(OperacionUsuarioPelicula op) throws SQLException;
}
