package es.ieslavereda.miraveredabackend.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {
    private int id;
    private String nombre;
}
