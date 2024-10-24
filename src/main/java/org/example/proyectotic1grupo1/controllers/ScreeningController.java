package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.services.ScreeningServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningServiceImpl screeningService;

    public ScreeningController(ScreeningServiceImpl screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings() {
        List<Screening> screenings = screeningService.findAll();
        return new ResponseEntity<>(screenings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable Long id) {
        try {
            Screening screening = screeningService.findById(id);
            return new ResponseEntity<>(screening, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        Screening newScreening = screeningService.save(screening);
        return new ResponseEntity<>(newScreening, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable Long id, @RequestBody Screening updatedScreening) {
        try {
            Screening screening = screeningService.update(id, updatedScreening);
            return new ResponseEntity<>(screening, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreening(@PathVariable Long id) {
        try {
            screeningService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

