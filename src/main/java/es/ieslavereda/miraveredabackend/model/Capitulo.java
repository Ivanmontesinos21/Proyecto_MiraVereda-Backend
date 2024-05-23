package es.ieslavereda.miraveredabackend.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Clase del Capítulo
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


public class Capitulo extends ContenidoAudiovisual {
    int idSerie;
    String serie;
    Date disponibleDesde;
    int temporada;
}
