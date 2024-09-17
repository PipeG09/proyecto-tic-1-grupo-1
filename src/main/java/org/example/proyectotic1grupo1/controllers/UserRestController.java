package org.example.proyectotic1grupo1.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.UserService;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserServiceImpl UserServiceImpl;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return UserServiceImpl.findAll();
    }


    @PutMapping("/profile")
    public void updateUserProfile(@ModelAttribute UserDto userDto, BindingResult result, Authentication authentication, HttpServletResponse response) throws IOException {
        if (result.hasErrors()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Datos inválidos");
            return;
        }
        String username = authentication.getName();
        try {
            userService.updateUserProfile(username, userDto);
            response.sendRedirect("/profile/success");
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Error al actualizar el perfil");
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            userService.updateUserById(id, userDto);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting user: " + e.getMessage());
        }
    }
}

