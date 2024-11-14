package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.Venue;
import org.example.proyectotic1grupo1.services.ScreeningServiceImpl;
import org.example.proyectotic1grupo1.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningServiceImpl screeningService;
    @Autowired
    UserSessionService userSessionService;
    public ScreeningController(ScreeningServiceImpl screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<?> getAllScreenings() {
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        List<Screening> screenings = screeningService.findAll();
        return ResponseEntity.ok(screenings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScreeningById(@PathVariable Long id) {
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        try {
            Screening screening = screeningService.findById(id);
            return ResponseEntity.ok(screening);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{venue_id}")
    public ResponseEntity<?> getScreeningByVenueId(@PathVariable Long venue_id) {
        List<Screening> screenings = null;
        try {
            screenings = screeningService.findByVenueId(venue_id);
            return ResponseEntity.ok(screenings);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/venues/{movieId}")
    public ResponseEntity<?> getVenuesByMovieId(@PathVariable Long movieId) {
        try {
            List<Venue> venues = screeningService.findVenueByMovieId(movieId);
            return ResponseEntity.ok(venues);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/screenings/{venueId}/{movieId}")
    public ResponseEntity<?> getScreeningsByVenueAndMovieId(@PathVariable Long venueId, @PathVariable Long movieId) {
        try {
            List<Screening> screenings = screeningService.findByVenueMovie(venueId, movieId);
            return ResponseEntity.ok(screenings);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}

