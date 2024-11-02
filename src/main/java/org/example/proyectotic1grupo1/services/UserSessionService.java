package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
   public boolean loggedIn(){
        return currentUser != null;
   }
}
