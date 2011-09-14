package controllers;

import models.User;

public class Security extends Secure.Security {
    
    static boolean authenticate(String username, String password) {
        User user = User.find("byEmailAndState", username, User.State.CONFIRMED).first();
        return user != null && user.authenticate(password);
    }
    
    
}