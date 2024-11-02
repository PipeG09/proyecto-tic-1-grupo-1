package org.example.proyectotic1grupo1.controllers;

import java.security.Principal;
import java.util.List;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.example.proyectotic1grupo1.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserSessionService userSessionService;

    private UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }




    @PostMapping("/login")
//    @PreAuthorize("permitAll()")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        User user = userService.findByUsername(userDto.getUsername());
        boolean isAuth = userService.validate(user,userDto.getPassword());
        if (isAuth) {
            userSessionService.setCurrentUser(user);
            return ResponseEntity.ok().body(user);
        }
        if (user == null){
            return ResponseEntity.notFound().build();}

        return ResponseEntity.badRequest().body("Password Does Not Match");

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user, Model model) {
        // Same as PreAuthorize("loggedIn()")
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        User user2 = userService.save(user);
        if (user == null) {
            return ResponseEntity.badRequest().body("User Already Exists");
        }
        return ResponseEntity.ok(user2);

    }

    @GetMapping("/users")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok().body(userService.findAll());
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
    public ResponseEntity<?> profile(Model model) {
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        User user = userSessionService.getCurrentUser();
         return ResponseEntity.ok().body(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        if (!userSessionService.loggedIn()){// check user is logged in
            return ResponseEntity.badRequest().body("Not logged in");
        }
        userSessionService.setCurrentUser(null);
        return ResponseEntity.ok().build();


    }

}
//
//$2a$10$mY3UohZONQAMU4VYiUVn7O/up4rT7hmDrIU7.K9LYSl2OiOXtpMS.
//$2a$10$cKLMumDN3BynjCcAwozDBOip6d4MvZdrlbx0LiTvPlkCy2lT72ErG
// $2a$10$L8BI.HSLdhw06Pkf6LoY6.3tMOeHZHoTbNclyeSqiqBQeeGk6HVeK
// $2a$10$dyxDhvy1pskSBl.RPT0rqeRaGCqt1Gl.91qbz7lp4ONJ7MmUxVBxK
//{"id":52,"username":"pipeG","password":"$2a$10$mY3UohZONQAMU4VYiUVn7O/up4rT7hmDrIU7.K9LYSl2OiOXtpMS.","fullname":"pipe","roles":[]}
//




