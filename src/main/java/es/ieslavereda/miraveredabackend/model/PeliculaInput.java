package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.util.Date;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaInput {
    private Integer id;
    private String tipo;
    private String titulo;
    private String descripcion;
    private String genero;
    private int duracion;
    private int fechaEstreno;
    private String nombreDirector;
    private int idTarifa;
    private int precio;
    private String versionIdioma;
    private Set<Integer> idActores;
    private Integer disponibleHasta;
    private Integer disponibleDesde;
    private Integer idSerie;
    private Integer temporada;

}
