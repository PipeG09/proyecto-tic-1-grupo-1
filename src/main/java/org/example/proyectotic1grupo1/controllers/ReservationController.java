package org.example.proyectotic1grupo1.controllers;

import jdk.jfr.Percentage;
import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.services.ReservationService;
import org.example.proyectotic1grupo1.services.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    UserSessionService userSessionService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/{screening}")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        if (!userSessionService.loggedIn()){// check user is logged in
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }


        if(reservation.getUserId()!=(userSessionService.getCurrentUser().getId())){ // user can only delete his own reservations
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        int savedReservation = reservationService.reserveSeats(reservation);
        if (savedReservation == 1) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.badRequest().build();
    }

    // Endpoint para obtener todas las reservas
    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations() {
        if (!userSessionService.loggedIn()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        User user = userSessionService.getCurrentUser();
        List<Reservation> reservations = reservationService.findAll(user);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/cancel/{reservation_id}")
    public ResponseEntity<?> cancelReservation(@PathVariable int reservation_id) {
        Reservation reservation = reservationService.findById(reservation_id);

        if (!userSessionService.loggedIn()){// check user is logged in
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        if(reservation.getUserId()!=(userSessionService.getCurrentUser().getId())){ // user can only delete his own reservations
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        int  cancelation = reservationService.cancelReservedSeats(reservation_id);
        if (cancelation == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}

