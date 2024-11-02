package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Venue;
import org.example.proyectotic1grupo1.services.UserSessionService;
import org.example.proyectotic1grupo1.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;
    @Autowired
    UserSessionService userSessionService;
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<?> getAllVenues() {
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        List<Venue> venues = venueService.findAll();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVenueById(@PathVariable Long id) {
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        try {
            Venue venue = venueService.findById(id);
            return new ResponseEntity<>(venue, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping
//    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
//        Venue newVenue = venueService.save(venue);
//        return new ResponseEntity<>(newVenue, HttpStatus.CREATED);
//    }


}

