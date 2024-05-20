package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private int precio;
    private int precioConTarifa;
    private String versionIdioma;
    private List<Actor> actores;

    public void fromCA(ContenidoAudiovisual ca, ResultSet rsActores) throws SQLException {
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
        precio = ca.precio;
        precioConTarifa = ca.precioConTarifa;
        versionIdioma = ca.versionIdioma;
        actores = new ArrayList<>();
        while(rsActores.next()) {
            actores.add(Actor.fromResultSet(rsActores));
        }
    }
}
