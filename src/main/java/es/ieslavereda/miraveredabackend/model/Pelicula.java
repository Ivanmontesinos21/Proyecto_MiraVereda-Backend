package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {
    private int id;
    private String nombre;
    private Genero genero;
    private Date disponible_hasta;
    private Date fechaestreno;
    private int duracion;
    private String titulo;
    private double precio;
    private String descripcion;
    private double media;
    private String director;
    private String version_idioma;
    private String idtarifa;
}
