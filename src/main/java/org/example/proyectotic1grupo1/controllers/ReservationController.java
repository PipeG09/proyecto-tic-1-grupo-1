package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Movie;
import org.example.proyectotic1grupo1.models.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

        @GetMapping("/{}")
        public ResponseEntity<Reservation> getMovieById(@PathVariable String rid) {
            return ResponseEntity.notFound().build();
        }



    }
}
