package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.*;
import es.ieslavereda.miraveredabackend.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio que se utiliza para dar respuesta a las acciones que se realizara en los ContenidosAudioVisuales
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    /**
     * Metodo que obtendra un ContenidoAudioVisual
     * @param id número que se enviara para poder identificar los ContenidosAudioVisuales
     * @return devolvera toda la informacion del ContenidoAudiovisual
     * @throws SQLException enviará una exepcion en el caso de que ese ContenidoAudiovisual no este
     */

    public ContenidoAudiovisualOutput getPelicula(int id) throws SQLException {
        return repository.getPelicula(id);
    }

    /**
     * Metodo que sirve para añadir el ContenidoAudiovisual
     * @param pelicula informacion del ContenidoAudiovisual que necesitaremos para crearlo
     * @return devolvera la respuesta del metodo de añadir el ContenidoAudiovisual del repositorio
     * @throws SQLException enviará una exepcion en el caso de que el ContenidoAudiovisual no se haya podido crear
     */

    public ContenidoAudiovisualOutput addPelicula(ContenidoAudiovisualInput pelicula) throws SQLException {
        return repository.addPelicula(pelicula);
    }

    /**
     * Metodo que se usa para actualizar un ContenidoAudiovisual
     * @param pelicula informacion que se utiliza para actualizar el ContenidoAudiovisual
     * @return devolvera la respuesta del reposiorio
     * @throws SQLException enviará una exepcion en el caso de que no se haya podido actualizar la información
     */

    public boolean updatePelicula(ContenidoAudiovisualInput pelicula) throws SQLException {
        return repository.updatePelicula(pelicula);
    }

    /**
     * Metodo que sirve para eliminar un ContenidoAudiovisual
     * @param id número identificador que servira para eliminar el ContenidoAudiovisual
     * @return devolvera la informacion del ContenidoAudiovisual que se haya eliminado
     * @throws SQLException enviará una exepcion en el caso de que ese ContenidoAudiovisual no exista
     */
    public ContenidoAudiovisualOutput deletePelicula(int id) throws SQLException {
        return repository.deletePelicula(id);
    }

    /**
     * Metodo que sirve para obtener una lista de todos ContenidosAudiovisuales
     * @param afterId id que tendra para cuando las mostremos
     * @return devolvera la informacion del metodo al que se invoca
     * @throws SQLException enviará una exepcion en el caso de que no haya ningun ContenidoAudiovisual que mostrar
     */
    public List<ContenidoAudiovisualOutput> getAllPeliculas(Integer afterId) throws SQLException {
        return repository.getAllPeliculas(afterId);
    }

    /**
     * Metodo que llamara al obtener el carrito del repositorio para poder enseñar una lista de lo que se ha añadido al carro
     * @param credenciales informacion del usuario que se requiere para obtener al carro
     * @return devolvera la lista de todos los ContenidosAudiovisuales que haya en el carrito
     * @throws SQLException enviará una exepcion en el caso de que ese cliente no tenga un carrito
     */

    public List<ContenidoAudiovisualOutput> getCarrito(Credenciales credenciales) throws SQLException {
        return repository.getCarrito(credenciales);
    }

    /**
     * Metodo que sirve para añadir al carro
     * @param op  informacion del cliente que necesitamos para poder realizar acciones en el carrito
     * @return devolvera un la id del ContenidoAudiovisual para poder añadirlo al carro
     * @throws SQLException enviará una exepcion en el caso de que se pierda la informacion del usuario
     */

    public int addCarrito(OperacionCarrito op) throws SQLException {
        return repository.addCarrito(op);
    }

    /**
     * Metodo que sirve para eliminar del carro
     * @param op informacion del cliente que necesitamos para poder acceder al carrito y hacer operaciones con el
     * @return devolvera la id del ContenidoAudiovisual que elimines
     * @throws SQLException enviará la exepcion si no se ha podido eliminar del carro el ContenidoAudiovisual
     */

    public int deleteCarrito(OperacionCarrito op) throws SQLException {
        return repository.deleteCarrito(op);
    }

    public int pagar(Credenciales credenciales) throws SQLException {
        return repository.pagar(credenciales);
    }

    public void addActor(Actor actor) throws SQLException {
        repository.addActor(actor);
    }
}
