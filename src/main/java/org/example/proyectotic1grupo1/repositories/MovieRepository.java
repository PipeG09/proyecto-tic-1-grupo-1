package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}