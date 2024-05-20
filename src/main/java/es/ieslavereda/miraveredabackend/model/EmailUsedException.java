package es.ieslavereda.miraveredabackend.model;

public class EmailUsedException extends Exception {
    public EmailUsedException() {
        super("La dirección de email especificada está en uso.");
    }
}
