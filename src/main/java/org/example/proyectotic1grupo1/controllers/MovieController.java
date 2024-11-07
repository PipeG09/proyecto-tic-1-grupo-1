package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Movie;
import org.example.proyectotic1grupo1.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id){
        Movie movie = movieService.findById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public List<Movie> getAllMovies(){
        return movieService.findAll();
    }



}
