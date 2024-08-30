package org.example.proyectotic1grupo1.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Puedes agregar atributos al modelo si es necesario
        // model.addAttribute("attributeName", "value");

        return "dashboard"; // Nombre del archivo HTML en src/main/resources/templates
    }
}
