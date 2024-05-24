package es.ieslavereda.miraveredabackend.controller;

import es.ieslavereda.miraveredabackend.model.*;
import es.ieslavereda.miraveredabackend.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PeliculaController extends BaseController {
    @Autowired
    private PeliculaService service;
    @Autowired
    private PeliculaService peliculaService;

    @CrossOrigin(origins = "*")
    @GetMapping("/pelicula/{id}")
    public ResponseEntity<?> getPelicula(@PathVariable int id) {
        try{
            ContenidoAudiovisualOutput pelicula = service.getPelicula(id);
            if(pelicula==null)
                return new ResponseEntity<>("MOVIE NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(pelicula,HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/pelicula/")
    public ResponseEntity<?> addPelicula(@RequestBody ContenidoAudiovisualInput pelicula) {
            try {
                return new ResponseEntity<>(peliculaService.addPelicula(pelicula), HttpStatus.OK);
            }catch (SQLException e) {
                Map<String,Object> response = new HashMap<>();
                response.put("code", e.getErrorCode());
                response.put("message", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @CrossOrigin(origins = "*")
    @PutMapping("/pelicula/")
    public ResponseEntity<?> updatePelicula(@RequestBody ContenidoAudiovisualInput pelicula) {
        try{
            boolean success = service.updatePelicula(pelicula);
            if(!success)
                return new ResponseEntity<>("MOVIE NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("",HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/pelicula/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable("id") int id) {
        try{
            ContenidoAudiovisualOutput pelicula = service.deletePelicula(id);
            if(pelicula==null)
                return new ResponseEntity<>("MOVIE NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(pelicula,HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pelicula/")
    public ResponseEntity<?> getAllPeliculas(@RequestParam(value = "after", required = false) Integer afterId) {
        try{
            return new ResponseEntity<>(service.getAllPeliculas(afterId), HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/carrito/ver/")
    public ResponseEntity<?> getCarrito(@RequestBody Credenciales credenciales) {
        try{
            return new ResponseEntity<>(service.getCarrito(credenciales), HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/carrito/")
    public ResponseEntity<?> addCarrito(@RequestBody OperacionUsuarioPelicula op) {
        try{
            return new ResponseEntity<>(service.addCarrito(op), HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/carrito/")
    public ResponseEntity<?> deleteCarrito(@RequestBody OperacionUsuarioPelicula op) {
        try{
            return new ResponseEntity<>(service.deleteCarrito(op), HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/pagar/")
    public ResponseEntity<?> pagar(@RequestBody Credenciales credenciales) {
        try{
            return new ResponseEntity<>(service.pagar(credenciales), HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/actor/")
    public ResponseEntity<?> addActor(@RequestBody Actor actor) {
        try{
            service.addActor(actor);
            return new ResponseEntity<>("", HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/pelicula/alquilada/")
    public ResponseEntity<?> estaAlquilada(@RequestBody OperacionUsuarioPelicula op) {
        try{
            return new ResponseEntity<>(service.estaAlquilada(op), HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code", e.getErrorCode());
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
