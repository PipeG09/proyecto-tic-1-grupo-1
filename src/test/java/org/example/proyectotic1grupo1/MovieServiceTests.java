package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.models.Movie;
import org.example.proyectotic1grupo1.repositories.MovieRepository;
import org.example.proyectotic1grupo1.services.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTests {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie1 = new Movie("Title1", "Description1", "Genre1", 120);
        movie2 = new Movie("Title2", "Description2", "Genre2", 100);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        // Act
        List<Movie> result = movieService.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        // Arrange
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));

        // Act
        Movie result = movieService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Movie result = movieService.findById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void testSave() {
        // Arrange
        when(movieRepository.save(movie1)).thenReturn(movie1);

        // Act
        Movie result = movieService.save(movie1);

        // Assert
        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
    }

    @Test
    void testUpdate() {
        // Arrange
        Movie updatedMovie = new Movie("Updated Title", "Updated Description", "Updated Genre", 150);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        when(movieRepository.save(movie1)).thenReturn(movie1);

        // Act
        Movie result = movieService.update(1L, updatedMovie);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertNotEquals(120, result.getDuration());
    }

    @Test
    void testUpdate_NotFound() {
        // Arrange
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Movie result = movieService.update(1L, movie1);

        // Assert
        assertNull(result);
    }

    @Test
    void testDeleteById() {
        // Arrange
        when(movieRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = movieService.deleteById(1L);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteById_NotFound() {
        // Arrange
        when(movieRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = movieService.deleteById(1L);

        // Assert
        assertFalse(result);
    }
}

