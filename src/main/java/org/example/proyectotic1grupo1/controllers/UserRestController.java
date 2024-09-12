package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userServiceImpl.findAll();
    }
}

