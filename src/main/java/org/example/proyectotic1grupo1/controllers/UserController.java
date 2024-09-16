package org.example.proyectotic1grupo1.controllers;

import java.security.Principal;
import java.util.List;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";  // Redirige a /index
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }



    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            System.out.println("Usuario autenticado: " + principal.getName());
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("username", principal.getName()); // Pasamos el nombre de usuario directamente

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        } else {
            System.out.println("Principal es null, el usuario no está autenticado.");
            model.addAttribute("username", null);
            model.addAttribute("isAdmin", false);
        }
        return "index";
    }


    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {

        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @GetMapping("users")
    public String users() {
        return "users";
    }

    @GetMapping("/profile")
    public String profile() {return "profile";}

    @PostMapping("/register")
    public String registerSava(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("Userexist", user);
            return "register";
        }
        userService.save(userDto);
        return "redirect:/register?success";
    }
}