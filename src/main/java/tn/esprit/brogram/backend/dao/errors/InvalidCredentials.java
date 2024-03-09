package tn.esprit.brogram.backend.dao.errors;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials(String message) {
        super(message);
    }
}
