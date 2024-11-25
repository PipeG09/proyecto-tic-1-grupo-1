package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name= "genre")
    private String genre;

    @Column(name = "duration",nullable=false)
    private int duration;

    @Column(name="image",columnDefinition = "TEXT")
    private String image;

    // Constructor vacío necesario para JPA
    public Movie() {
    }

    // Constructor con parámetros
    public Movie(String title, String description, String genre, int duration) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
    }

    // Getters y setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}