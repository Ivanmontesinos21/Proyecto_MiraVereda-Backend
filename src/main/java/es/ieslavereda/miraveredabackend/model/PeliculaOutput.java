package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaOutput {
    private int id;
    private String tipo;
    private String titulo;
    private String descripcion;
    private String genero;
    private int duracion;
    private int fechaEstreno;
    private String nombreDirector;
    private double valoracionMedia;
    private int idTarifa;
    private int precio;
    private int precioConTarifa;
    private String versionIdioma;
    private List<Actor> actores;
    private Integer disponibleHasta;
    private Integer disponibleDesde;
    private Integer idSerie;
    private String serie;
    private Integer temporada;
}
