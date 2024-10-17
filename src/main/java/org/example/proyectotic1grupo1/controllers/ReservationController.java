package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.models.Reservation;
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
        Reservation savedReservation = reservationService.reserveSeats(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    // Endpoint para obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservations);
    }

    // Endpoint para obtener una reserva específica por su combinación de clave primaria
    @GetMapping("/{screeningId}/{userId}/{seatRow}/{seatColumn}")
    public ResponseEntity<Reservation> getReservation(
            @PathVariable String screeningId,
            @PathVariable String userId,
            @PathVariable short seatRow,
            @PathVariable short seatColumn) {
        Reservation reservation = reservationService.findById(screeningId, userId, seatRow, seatColumn);
        return ResponseEntity.ok(reservation);
    }
}

