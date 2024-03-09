package tn.esprit.brogram.backend.dao.errors;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
