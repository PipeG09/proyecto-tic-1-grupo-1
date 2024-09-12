package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserServiceImpl UserServiceImpl;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return UserServiceImpl.findAll();
    }
}

