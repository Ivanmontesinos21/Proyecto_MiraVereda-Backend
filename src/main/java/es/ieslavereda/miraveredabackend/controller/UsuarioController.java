package es.ieslavereda.miraveredabackend.controller;

import es.ieslavereda.miraveredabackend.model.*;
import es.ieslavereda.miraveredabackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador del Cliente
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    /**
     * Obtiene un cliente por su ID.
     *
     * @param id el ID del cliente
     * @return el cliente encontrado o un mensaje de error si no se encuentra
     */

    @CrossOrigin(origins = "*")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable int id) {
        try{
            UsuarioOutput usuario = service.getUsuario(id);
            if(usuario==null)
                return new ResponseEntity<>("USER NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(usuario,HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Añade un nuevo cliente.
     *
     * @param usuarioInput la información del cliente a añadir
     * @return el cliente añadido o un mensaje de error si ocurre cualquier tipo un problema
     */

    @CrossOrigin(origins = "*")
    @PostMapping("/usuario/")
    public ResponseEntity<?> addUsuario(@RequestBody UsuarioInput usuarioInput) {
        try{
            UsuarioOutput usuario = service.addUsuario(usuarioInput);
            if(usuario == null)
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        catch (SQLException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (EmailUsedException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Actualiza un cliente existente.
     *
     * @param usuarioInput  la información del cliente a actualizar
     * @return un mensaje de éxito o error según corresponda
     */

    @CrossOrigin(origins = "*")
    @PutMapping("/usuario/")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioInput usuarioInput) {
        try{
            boolean success = service.updateUsuario(usuarioInput);
            if(!success)
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (EmailUsedException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id el ID del cliente
     * @return el cliente eliminado o un mensaje de error si no se encuentra
     */

    @CrossOrigin(origins = "*")
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable int id) {
        try{
            UsuarioOutput usuario = service.deleteUsuario(id);
            if(usuario==null)
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Obtiene todos los clientes.
     *
     * @return la lista de clientes
     */

    @CrossOrigin(origins = "*")
    @GetMapping("/usuario/")
    public ResponseEntity<?> getAllUsuarios() {
        try{
            return new ResponseEntity<>(service.getAllUsuarios(),HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Autentica a un cliente.
     *
     * @param credenciales las credenciales del cliente
     * @return la información del cliente autenticado o un mensaje de error si no se encuentra
     */

    @CrossOrigin(origins = "*")
    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody Credenciales credenciales) {
        try{
            UsuarioOutput usuario = service.login(credenciales.getEmail(), credenciales.getContrasenya());
            if(usuario == null)
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        catch (SQLException e){
            Map<String, Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Restablece la contraseña de un cliente.
     *
     * @param credenciales las credenciales del cliente
     * @return un mensaje de éxito o error según corresponda
     */

    @CrossOrigin(origins = "*")
    @PutMapping("/resetpass/")
    public ResponseEntity<?> resetPass(@RequestBody Credenciales credenciales) {
        try{
            boolean success = service.resetPass(credenciales);
            if(!success)
                return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        catch (SQLException e){
            Map<String, Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
