package es.ieslavereda.miraveredabackend.model;

import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CapituloOutput extends ContenidoAudiovisualOutput {
    int idSerie;
    String serie;
    long disponibleDesde;
    int temporada;

    public void fromCA(Capitulo capitulo, ResultSet rsActores) throws SQLException {
        super.fromCA(capitulo, rsActores);
        idSerie = capitulo.idSerie;
        serie = capitulo.serie;
        disponibleDesde = capitulo.disponibleDesde.getTime() / 1000;
        temporada = capitulo.temporada;
    }
}
