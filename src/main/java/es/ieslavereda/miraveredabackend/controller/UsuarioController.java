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

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

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

    @CrossOrigin(origins = "*")
    @PutMapping("/usuario/")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioInput usuarioInput) {
        try{
            service.updateUsuario(usuarioInput);
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
}
