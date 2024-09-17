package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Component
@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addAttributes(Model model, Principal principal) {
        boolean isAuthenticated = false;
        boolean isAdmin = false;
        String username = null;

        if (principal != null) {
            isAuthenticated = true;
            username = principal.getName();

            // Obtener los roles del usuario
            User user = userService.findByUsername(username);
            if (user != null) {
                isAdmin = userService.isAdmin(user);
            }
        }

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", username);
    }
}
