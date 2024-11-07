package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;


@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long movieId;
    @Column(name="title", nullable=false)
    String title ;
    @Column(name = "description")
    String description;
    @Column(name= "genre")
    String genre;
    @Column(name = "duration",nullable=false)
    int duration ;

    @Column(name="image",nullable = false)
    String image ;

    public Movie(String title, String description, String genre, int duration) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
    }

    public Movie() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
