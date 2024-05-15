package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.PeliculaInput;
import es.ieslavereda.miraveredabackend.model.PeliculaOutput;
import es.ieslavereda.miraveredabackend.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public PeliculaOutput getPelicula(int id) throws SQLException {
        return repository.getPelicula(id);
    }

    public Integer addPelicula(PeliculaInput pelicula) throws SQLException {
        return repository.addPelicula(pelicula);
    }

    public boolean updatePelicula(PeliculaInput pelicula) throws SQLException {
        return repository.updatePelicula(pelicula);
    }
    public PeliculaOutput deletePelicula(int id) throws SQLException {
        return repository.deletePelicula(id);
    }
    public List<PeliculaOutput> getAllPeliculas() throws SQLException {
        return repository.getAllPeliculas();
    }

}
