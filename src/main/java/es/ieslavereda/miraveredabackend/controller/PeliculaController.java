package es.ieslavereda.miraveredabackend.controller;

import es.ieslavereda.miraveredabackend.model.Pelicula;
import es.ieslavereda.miraveredabackend.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class PeliculaController extends BaseController {
    @Autowired
    private PeliculaService service;
    @Autowired
    private PeliculaService peliculaService;

    private final Logger LOG = Logger.getLogger(getClass().getCanonicalName());

    @GetMapping("/pelicula/{id}")
    public ResponseEntity<?> getPelicula(@PathVariable("id") int id) {
        try{
            Pelicula pelicula = service.getPelicula(id);
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

    @PostMapping("/pelicula/")
    public ResponseEntity<?> addPelicula(@RequestBody Pelicula pelicula) {
            try {
                LOG.log(Level.INFO,"Añadiendo Usuarios");
                return new ResponseEntity<>(peliculaService.addPelicula(pelicula), HttpStatus.OK);
            }catch (SQLException e) {
                return new ResponseEntity<>("Error al añadir usuario", HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    @PutMapping("/pelicula/")
    public ResponseEntity<?> updatePelicula(@RequestBody Pelicula pelicula) {
        try{
            boolean success = service.updatePelicula(pelicula);
            if(!success)
                return new ResponseEntity<>("MOVIE NOT FOUND",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("",HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pelicula/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable("id") int id) {
        try{
            Pelicula pelicula = service.deletePelicula(id);
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

    @GetMapping("/pelicula/")
    public ResponseEntity<?> getAllPeliculas() {
        try{
            return new ResponseEntity<>(service.getAllPeliculas(),HttpStatus.OK);
        }  catch (SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put(("message"),e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
