package es.ieslavereda.miraveredabackend.model;

import lombok.*;

import java.util.Set;
/**
 * Clase del ContenidoAudivisual que recibiremos cuando haya que añadirlo a la base de datos
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContenidoAudiovisualInput {
    private Integer id;
    private String tipo;
    private String titulo;
    private String descripcion;
    private String genero;
    private int duracion;
    private long fechaEstreno;
    private String nombreDirector;
    private int idTarifa;
    private String imagenUrl;
    private int precio;
    private String versionIdioma;
    private Set<Integer> idActores;
    private Long disponibleHasta;
    private Long disponibleDesde;
    private Integer idSerie;
    private Integer temporada;

}
