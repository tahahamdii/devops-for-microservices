package tn.esprit.brogram.backend.dao.errors;

public class PasswordDoesNotMatchTheOld extends RuntimeException{
    public PasswordDoesNotMatchTheOld(String message) {
        super(message);
    }
}
