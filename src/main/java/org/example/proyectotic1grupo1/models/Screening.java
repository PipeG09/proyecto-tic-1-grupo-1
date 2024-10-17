package org.example.proyectotic1grupo1.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long screeningId; // primary key

    @Column(name = "venueId")
    private long venueId;

    @Column(name="movieId")
    private long movieId;

    @Column(name="date")
    private LocalDateTime date;

    public Screening() {
    }

    public Screening(long venueId, long movieId, LocalDateTime date) {
        this.venueId = venueId;
        this.movieId = movieId;
        this.date = date;
    }

    public long getVenueId() {
        return venueId;
    }

    public void setVenueId(long venueId) {
        this.venueId = venueId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
