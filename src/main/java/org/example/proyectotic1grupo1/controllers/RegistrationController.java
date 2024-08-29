package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Nombre del template de Thymeleaf para la vista de registro
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Verifica si el nombre de usuario o el correo electrónico ya existen
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "register";
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "El correo electrónico ya está en uso");
            return "register";
        }

        // Guardar el nuevo usuario
        userService.save(user);
        return "redirect:/login";  // Redirigir a la página de inicio de sesión después del registro exitoso
    }
}