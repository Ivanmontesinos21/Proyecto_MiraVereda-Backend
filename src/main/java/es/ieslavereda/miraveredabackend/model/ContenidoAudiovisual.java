package es.ieslavereda.miraveredabackend.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase del ConteidoAudiovisual
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


public class ContenidoAudiovisual {
    Integer id;
    String tipo;
    String genero;
    Date fechaEstreno;
    int duracion;
    String titulo;
    int precio;
    int precioConTarifa;
    String descripcion;
    double valoracionMedia;
    String nombreDirector;
    String versionIdioma;
    int idTarifa;
    String imagenUrl;

    /**
     * Metodo que sirve para convertir de un ResultSet a un ContenidoAudioVisual
     * @param resultSet resultset que enviaremos
     * @return devolvera toda la informacion del ContenidoAudioVisual
     * @throws SQLException enviará una exepcion en el caso de que no pueda añadir los parametros
     */

    public static ContenidoAudiovisual fromResultSet(ResultSet resultSet) throws SQLException {
        String tipo = resultSet.getString("tipo");
        ContenidoAudiovisual ca;
        if(tipo.equals("pelicula")) {
            Pelicula pelicula = new Pelicula();
            pelicula.disponibleHasta = resultSet.getDate("disponible_hasta");
            ca = pelicula;
        }
        else if(tipo.equals("capitulo")) {
            Capitulo capitulo = new Capitulo();
            capitulo.idSerie = resultSet.getInt("codigo_serie");
            capitulo.serie = resultSet.getString("serie");
            capitulo.disponibleDesde = resultSet.getDate("disponible_desde");
            capitulo.temporada = resultSet.getInt("temporada");
            ca = capitulo;
        }
        else
            ca = new ContenidoAudiovisual();
        ca.tipo = tipo;
        ca.id = resultSet.getInt("id_ca");
        ca.genero = resultSet.getString("genero");
        ca.fechaEstreno = resultSet.getDate("fecha_estreno");
        ca.duracion = resultSet.getInt("duracion");
        ca.titulo = resultSet.getString("titulo");
        ca.precio = resultSet.getInt("precio");
        ca.precioConTarifa = resultSet.getInt("precio_con_tarifa");
        ca.descripcion = resultSet.getString("descripcion");
        ca.valoracionMedia = resultSet.getDouble("valoracion_media");
        ca.nombreDirector = resultSet.getString("nombre_director");
        ca.versionIdioma = resultSet.getString("version_idioma");
        ca.idTarifa = resultSet.getInt("id_tarifa");
        ca.imagenUrl = resultSet.getString("imagen_url");
        return ca;
    }
}
