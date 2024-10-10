package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.Movie;
import org.example.proyectotic1grupo1.models.Role;
import org.example.proyectotic1grupo1.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @GetMapping("/{title}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String title){
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Movie> postMovie(String title, String description, String genre, int duration){
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Movie> getAllMovies(){
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Movie> deleteMovieByTitle(String title){
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(String title, String description, String genre, int duration){
        return ResponseEntity.notFound().build();
    }
}
