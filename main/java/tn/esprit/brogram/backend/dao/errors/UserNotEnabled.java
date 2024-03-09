package tn.esprit.brogram.backend.dao.errors;

public class UserNotEnabled extends RuntimeException{
    public UserNotEnabled(String message) {
        super(message);
    }
}
