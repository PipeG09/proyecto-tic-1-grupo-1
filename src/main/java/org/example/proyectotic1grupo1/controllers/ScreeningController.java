package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Screening;
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


}

