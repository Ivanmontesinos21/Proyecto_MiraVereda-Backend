package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.Pelicula;
import es.ieslavereda.miraveredabackend.model.Pelicula;
import es.ieslavereda.miraveredabackend.repository.PeliculaRepository;
import es.ieslavereda.miraveredabackend.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public Pelicula getPelicula(int id) throws SQLException {
        return repository.getPelicula(id);
    }

    public Integer addPelicula(Pelicula pelicula) throws SQLException {
        return repository.addPelicula(pelicula);
    }

    public boolean updatePelicula(Pelicula pelicula) throws SQLException {
        return repository.updatePelicula(pelicula);
    }
    public Pelicula deletePelicula(int id) throws SQLException {
        return repository.deletePelicula(id);
    }
    public List<Pelicula> getAllPeliculas() throws SQLException {
        return repository.getAllPeliculas();
    }

}
