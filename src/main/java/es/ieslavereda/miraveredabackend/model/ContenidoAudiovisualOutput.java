package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase del ContenidoAudiovisual que eseñaremos en el caso de que nos pidan informacion
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


@Getter
public class ContenidoAudiovisualOutput {
    private int id;
    private String tipo;
    private String titulo;
    private String descripcion;
    private String genero;
    private int duracion;
    private long fechaEstreno;
    private String nombreDirector;
    private double valoracionMedia;
    private int idTarifa;
    private String imagenUrl;
    private int precio;
    private int precioConTarifa;
    private String versionIdioma;
    private List<Actor> actores;
    private Long disponibleHasta;
    private Integer idSerie;
    private String serie;
    private Long disponibleDesde;
    Integer temporada;

    /**
     * Metodo que sirve para de un resultset de actores añadirlos al ContenidoAudioVisual
     * @param ca ContenidoAudioVisual que enviaremos
     * @param rsActores resultset de actores que enviaremos para añadirlos al ContenidoAudioVisual
     * @throws SQLException enviaremos una exepcion si no hemos podido añadirlos
     */

    public ContenidoAudiovisualOutput(ContenidoAudiovisual ca, ResultSet rsActores) throws SQLException {
        id = ca.id;
        tipo = ca.tipo;
        titulo = ca.titulo;
        descripcion = ca.descripcion;
        genero = ca.genero;
        duracion = ca.duracion;
        fechaEstreno = ca.fechaEstreno.getTime() / 1000;
        nombreDirector = ca.nombreDirector;
        valoracionMedia = ca.valoracionMedia;
        idTarifa = ca.idTarifa;
        imagenUrl = ca.imagenUrl;
        precio = ca.precio;
        precioConTarifa = ca.precioConTarifa;
        versionIdioma = ca.versionIdioma;
        actores = new ArrayList<>();
        if(rsActores != null) {
            while(rsActores.next()) {
                actores.add(Actor.fromResultSet(rsActores));
            }
        }
        if(ca instanceof Pelicula) {
            disponibleHasta = ((Pelicula)ca).disponibleHasta.getTime() / 1000;
        }
        else if(ca instanceof Capitulo) {
            Capitulo capitulo = (Capitulo)ca;
            idSerie = capitulo.idSerie;
            serie = capitulo.serie;
            disponibleDesde = capitulo.disponibleDesde.getTime() / 1000;
            temporada = capitulo.temporada;
        }
    }
}
