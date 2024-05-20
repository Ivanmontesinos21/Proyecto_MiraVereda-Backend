package es.ieslavereda.miraveredabackend.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pelicula extends ContenidoAudiovisual {
    Date disponibleHasta;

    @Override
    public ContenidoAudiovisualOutput toCAOutput(ResultSet rsActores) throws SQLException {
        PeliculaOutput peliculaOutput = new PeliculaOutput();
        peliculaOutput.fromCA(this, rsActores);
        return peliculaOutput;
    }
}
