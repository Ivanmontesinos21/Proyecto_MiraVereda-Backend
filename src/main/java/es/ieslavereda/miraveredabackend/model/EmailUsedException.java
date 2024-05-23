package es.ieslavereda.miraveredabackend.model;
/**
 * Exepcion creada para poder controlar que no se repitan emails
 * @Version 1.0 2024/05/23
 * @Author David,Ian,Jaime,Ivan
 */



public class EmailUsedException extends Exception {
    public EmailUsedException() {
        super("La dirección de email especificada está en uso.");
    }
}
