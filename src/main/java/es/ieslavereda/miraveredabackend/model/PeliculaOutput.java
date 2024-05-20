package es.ieslavereda.miraveredabackend.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeliculaOutput extends ContenidoAudiovisualOutput {
    long disponibleHasta;

    public void fromCA(Pelicula pelicula, ResultSet rsActores) throws SQLException {
        super.fromCA(pelicula, rsActores);
        disponibleHasta = pelicula.disponibleHasta.getTime() / 1000;
    }
}
