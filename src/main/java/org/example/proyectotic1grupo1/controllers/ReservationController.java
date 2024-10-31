package org.example.proyectotic1grupo1.controllers;

import jdk.jfr.Percentage;
import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations") // Ruta base para las reservas
public class ReservationController {


    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/{screening}")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        int savedReservation = reservationService.reserveSeats(reservation);
        if (savedReservation == 1) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.badRequest().build();
    }

    // Endpoint para obtener todas las reservas
    @GetMapping("/reservations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Reservation>> getAllReservations(Model model) {
        User user = (User) model.getAttribute("user");
        List<Reservation> reservations = reservationService.findAll(user);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/cancel/{reservation_id}")
    public ResponseEntity<?> cancelReservation(@PathVariable int reservation_id) {
        int  cancelation = reservationService.cancelReservedSeats(reservation_id);
        if (cancelation == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}

