package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.services.ScreeningServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningServiceImpl screeningService;

    public ScreeningController(ScreeningServiceImpl screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings() {
        List<Screening> screenings = screeningService.findAll();
        return ResponseEntity.ok(screenings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable Long id) {
        try {
            Screening screening = screeningService.findById(id);
            return ResponseEntity.ok(screening);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}

