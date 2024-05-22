package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.ContenidoAudiovisualInput;
import es.ieslavereda.miraveredabackend.model.ContenidoAudiovisualOutput;
import es.ieslavereda.miraveredabackend.model.Credenciales;
import es.ieslavereda.miraveredabackend.model.OperacionCarrito;
import es.ieslavereda.miraveredabackend.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public ContenidoAudiovisualOutput getPelicula(int id) throws SQLException {
        return repository.getPelicula(id);
    }

    public ContenidoAudiovisualOutput addPelicula(ContenidoAudiovisualInput pelicula) throws SQLException {
        return repository.addPelicula(pelicula);
    }

    public boolean updatePelicula(ContenidoAudiovisualInput pelicula) throws SQLException {
        return repository.updatePelicula(pelicula);
    }
    public ContenidoAudiovisualOutput deletePelicula(int id) throws SQLException {
        return repository.deletePelicula(id);
    }
    public List<ContenidoAudiovisualOutput> getAllPeliculas(Integer afterId) throws SQLException {
        return repository.getAllPeliculas(afterId);
    }

    public List<ContenidoAudiovisualOutput> getCarrito(Credenciales credenciales) throws SQLException {
        return repository.getCarrito(credenciales);
    }

    public int addCarrito(OperacionCarrito op) throws SQLException {
        return repository.addCarrito(op);
    }

    public int deleteCarrito(OperacionCarrito op) throws SQLException {
        return repository.deleteCarrito(op);
    }

    public int pagar(Credenciales credenciales) throws SQLException {
        return repository.pagar(credenciales);
    }
}
