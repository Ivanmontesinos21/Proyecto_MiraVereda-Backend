package es.ieslavereda.miraveredabackend.controller;

import es.ieslavereda.miraveredabackend.model.Usuario;
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

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable("id") int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        try{
            Usuario usuario = service.getUsuario(id);
            if(usuario==null)
                return new ResponseEntity<>("USER NOT FOUND",headers,HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(usuario,headers,HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuario/")
    public ResponseEntity<?> addUsuario(@RequestBody Usuario usuario) {

        return null;
    }

    @PutMapping("/usuario/")
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario) {
        try{
            boolean success = service.updateUsuario(usuario);
            if(!success)
                return new ResponseEntity<>("USER NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("",HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") int id) {
        try{
            Usuario usuario = service.deleteUsuario(id);
            if(usuario==null)
                return new ResponseEntity<>("USER NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(usuario,HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario/")
    public ResponseEntity<?> getAllUsuarios() {
        try{
            return new ResponseEntity<>(service.getAllUsuarios(),HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
