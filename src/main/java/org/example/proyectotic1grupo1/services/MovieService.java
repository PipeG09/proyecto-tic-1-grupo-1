package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.Movie;
import java.util.List;

public interface MovieService {

    List<Movie> findAll();

    Movie findById(Long id);

    Movie save(Movie movie);

    Movie update(Long id, Movie movieDetails);

    boolean deleteById(Long id);
}
