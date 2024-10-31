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
@RequestMapping("/api")
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
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = userService.findByUsername(username);
        boolean isAuth = userService.validate(user,password);
        if (isAuth) {
            model.addAttribute("user", user);
            model.addAttribute("isAuthenticated", true);
            return ResponseEntity.ok().body(user);
        }
        if (user == null){
            return ResponseEntity.badRequest().body("User Does Not Exist");
        }
        return ResponseEntity.badRequest().body("Password Does Not Match");

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam("fullname") String fullname,Model model) {
        User user = userService.save(username,password,fullname);
        if (user == null) {
            return ResponseEntity.badRequest().body("User Already Exists");
        }
        return login(username,password,model);

    }

    @GetMapping("/users")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok().body(userServiceImpl.findAll());
    }



    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> profile(Model model, Principal principal) {
         User user = (User) model.getAttribute("user");
         return ResponseEntity.ok().body(user);
    }



}