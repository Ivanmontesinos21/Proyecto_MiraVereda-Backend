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

/**
 * Controlador de las Peliculas
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */

@RestController
@RequestMapping("/api")
public class PeliculaController extends BaseController {
    @Autowired
    private PeliculaService service;
    @Autowired
    private PeliculaService peliculaService;

    /**
     * Obtiene un ContenidoAudiovisual por su ID.
     *
     * @param id el ID del ContenidoAudiovisual
     * @return el ContenidoAudiovisual  encontrado o un mensaje de error si no se encuentra
     */

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

    /**
     * Añade un nuevo ContenidoAudiovisual.
     *
     * @param pelicula el ContenidoAudiovisual a añadir
     * @return el ContenidoAudiovisual añadido o un mensaje de error si ocurre un problema
     */

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
    /**
     * Actualiza el ContenidoAudiovisual existente.
     *
     * @param pelicula el ContenidoAudiovisual a actualizar
     * @return un mensaje de éxito o error según corresponda
     */

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

    /**
     * Elimina el ContenidoAudiovisual por su ID.
     *
     * @param id el ID del ContenidoAudiovisual
     * @return el ContenidoAudiovisual eliminado o un mensaje de error si no se encuentra
     */

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

    /**
     * Obtiene todos los  ContenidosAudiovisuales.
     *
     * @param afterId ID del ContenidoAudiovisual a partir de la cual obtener las siguientes películas
     * @return la lista de los  ContenidoAudiovisual
     */

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

    /**
     * Obtiene el carrito de compras de un usuario.
     *
     * @param credenciales las credenciales del usuario
     * @return el carrito de compras del usuario
     */

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

    /**
     * Añade una película al carrito de compras.
     *
     * @param op la operación de usuario y película
     * @return la operación realizada
     */

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
    /**
     * Elimina una película del carrito de compras.
     *
     * @param op la operación de usuario y película
     * @return la operación realizada
     */

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

    /**
     * Realiza el pago del carrito de compras.
     *
     * @param credenciales las credenciales del usuario
     * @return la operación de pago realizada
     */

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
    /**
     * Añade un nuevo actor.
     *
     * @param actor el actor a añadir
     * @return un mensaje de éxito o de error
     */

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

    /**
     * Verifica si una película está alquilada.
     *
     * @param op la operación de usuario y película
     * @return verdadero si la película está alquilada, falso en caso contrario
     */


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
