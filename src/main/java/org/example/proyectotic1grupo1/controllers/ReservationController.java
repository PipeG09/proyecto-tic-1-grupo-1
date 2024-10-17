package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations") // Ruta base para las reservas
public class ReservationController {


    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Endpoint para crear una nueva reserva
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        int savedReservation = reservationService.reserveSeats(reservation);
        if (savedReservation == 1) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.badRequest().build();
    }

    // Endpoint para obtener todas las reservas
    @GetMapping("/{userId}")
    public ResponseEntity<List<Reservation>> getAllReservations(@RequestBody User user) {
        List<Reservation> reservations = reservationService.findAll(user);
        return ResponseEntity.ok(reservations);
    }


}

