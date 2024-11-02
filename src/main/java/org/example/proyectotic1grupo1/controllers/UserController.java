package org.example.proyectotic1grupo1.controllers;

import java.security.Principal;
import java.util.List;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    public UserController(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto,Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        boolean isAuth = userService.validate(user,userDto.getPassword());
        if (isAuth) {
            model.addAttribute("user", user);
            model.addAttribute("isAuthenticated", true);
            return ResponseEntity.ok().body(user);
        }
        if (user == null){
            return ResponseEntity.notFound().build();}

        return ResponseEntity.badRequest().body("Password Does Not Match");

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, Model model) {
        User user2 = userService.save(user);
        if (user == null) {
            return ResponseEntity.badRequest().body("User Already Exists");
        }
        return ResponseEntity.ok(user2);

    }

    @GetMapping("/users")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok().body(userServiceImpl.findAll());
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unknown Error");
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> profile(Model model) {
         User user = (User) model.getAttribute("user");
         return ResponseEntity.ok().body(user);
    }



}