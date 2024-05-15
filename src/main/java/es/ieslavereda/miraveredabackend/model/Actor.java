package es.ieslavereda.miraveredabackend.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private int id;
    private String nombre;
    private String apellidos;
}
