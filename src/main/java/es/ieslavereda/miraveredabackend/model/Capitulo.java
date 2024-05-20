package es.ieslavereda.miraveredabackend.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Capitulo extends ContenidoAudiovisual {
    int idSerie;
    String serie;
    Date disponibleDesde;
    int temporada;

    @Override
    public ContenidoAudiovisualOutput toCAOutput(ResultSet rsActores) throws SQLException {
        CapituloOutput capituloOutput = new CapituloOutput();
        capituloOutput.fromCA(this, rsActores);
        return capituloOutput;
    }
}
