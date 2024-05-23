package es.ieslavereda.miraveredabackend.service;

import es.ieslavereda.miraveredabackend.model.*;
import es.ieslavereda.miraveredabackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Servicio que se utiliza para dar respuesta a las acciones que realizara Cliente
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    /**
     * Metodo sirve para llamar al repositorio para dar respuesta a esta peticion para obtener un usuario
     * @param id número de identificador que pasaremos para obtener el cliente
     * @return devolvera la respuesta del metodo que está en el repositorio
     * @throws SQLException enviará una exepcion en el caso que del repositorio envie una exepcion
     */

    public UsuarioOutput getUsuario(int id) throws SQLException {
        return repository.getUsuario(id);
    }

    /**
     * Metodo que sirve para llamar al repositorio para dar respuesta a esta peticion para añadir a un cliente
     * @param usuario información necesaria para poder crear al cliente
     * @return devolvera la respuesta del metodo al que está invocando
     * @throws SQLException exepcion que sucerdera en caso de que no se pueda realizar la accion correspondiente
     * @throws EmailUsedException exepcion creada para que no se puedan repetir los emails
     */

    public UsuarioOutput addUsuario(UsuarioInput usuario) throws SQLException, EmailUsedException {
        return repository.addUsuario(usuario);
    }

    /**
     * Metodo que sirve para llamar al repositorio para dar respuesta a esta peticion de actualizar al cliente
     * @param usuario informacion que se necesita para modificar al cliente
     * @return devolvera la respuesta del metodo sí se ha podido actualizar
     * @throws SQLException exepcion que sucedera si el metodo no ha podido realizar la accion
     * @throws EmailUsedException exepcion que sirve para saber si el email esta repetido
     */

    public boolean updateUsuario(UsuarioInput usuario) throws SQLException, EmailUsedException {
        return repository.updateUsuario(usuario);
    }

    /**
     * Metodo que sirve para llamar al repositorio para dar respuesta a esta peticion de eliminar al cliente
     * @param id número identificador que nos servira para eliminar al cliente correspondiente
     * @return devolvera la respuesta del metodo que está en el repositorio
     * @throws SQLException exepcion que se enviara en el caso de que la id correspondiente no este dentro de la base de datos
     */
    public UsuarioOutput deleteUsuario(int id) throws SQLException {
        return repository.deleteUsuario(id);
    }

    /**
     * Metodo que sirve para llamar al repositorio para dar respuesta a esta peticion de obtener todos los clientes
     * @return devolvera una Lista de todos los clientes que haya
     * @throws SQLException exepcion que se enviara en caso de que no se haya podido enviar la lista
     */
    public List<UsuarioOutput> getAllUsuarios() throws SQLException {
        return repository.getAllUsuarios();
    }

    /**
     * Metodo que sirve para llamar al repositorio para dar respuesta a la peticion de logearte en la aplicacion
     * @param email mail que se utliza para poder acceder en la aplicacion
     * @param contrasenya contraseña que se usa para acceder a la aplicacion
     * @return devolvera respuesta del metodo al que se está haciendo la llamada
     * @throws SQLException exepcion que se enviara en el caso de que no se pueda realizar el login
     */

    public UsuarioOutput login(String email, String contrasenya) throws SQLException {
        return repository.login(email, contrasenya);
    }

    /**
     * Metodo que sirve para llamar al repositorio para dar respuesta a la peticion de resetear la contrasña del cliente
     * @param credenciales objeto que contiene la informacion del cliente tanto como su contraseña como su mail
     * @return devolvera la respuesta del metodo resetpass del repositorio
     * @throws SQLException enviará una exepcion en el caso de que no se pueda resetear la contraseña
     */

    public boolean resetPass(Credenciales credenciales) throws SQLException {
        return repository.resetPass(credenciales);
    }
}
