package org.example.proyectotic1grupo1.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")  // Garantiza que solo ADMIN pueda acceder
    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("message", "Bienvenido, Administrador!");
        return "admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminprofile")
    public String adminProfile(Model model) {
        return "adminprofile";
    }
}