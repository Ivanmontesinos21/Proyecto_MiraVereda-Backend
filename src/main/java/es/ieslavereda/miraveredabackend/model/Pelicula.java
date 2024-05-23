package es.ieslavereda.miraveredabackend.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que hereda del ContenidoAudioVisual y se añade un atributo
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */



public class Pelicula extends ContenidoAudiovisual {
    Date disponibleHasta;
}
