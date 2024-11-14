package org.example.proyectotic1grupo1.controllers;

import jdk.jfr.Percentage;
import org.example.proyectotic1grupo1.models.*;
import org.example.proyectotic1grupo1.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    UserSessionService userSessionService;

    @Autowired
    ScreeningService screeningService;

    @Autowired
    MovieService movieService;

    @Autowired
    VenueService venueService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations() {
        if (!userSessionService.loggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        try {
            User user = userSessionService.getCurrentUser();
            List<Reservation> reservations = reservationService.findAll(user);

            // Enriquecer las reservas con la información completa
            List<Map<String, Object>> enrichedReservations = new ArrayList<>();

            for (Reservation reservation : reservations) {
                Map<String, Object> enrichedReservation = new HashMap<>();
                // Datos básicos de la reserva
                enrichedReservation.put("reservationId", reservation.getReservationId());
                enrichedReservation.put("seatRow", reservation.getSeatRow());
                enrichedReservation.put("seatColumn", reservation.getSeatColumn());

                // Obtener y agregar datos del screening
                Screening screening = screeningService.findById(reservation.getScreeningId());
                if (screening != null) {
                    Map<String, Object> screeningData = new HashMap<>();
                    screeningData.put("screeningId", screening.getScreeningId());
                    screeningData.put("date", screening.getDate());

                    // Obtener y agregar datos de la película
                    Movie movie = movieService.findById(screening.getMovieId());
                    if (movie != null) {
                        screeningData.put("movie", movie);
                    }

                    // Obtener y agregar datos del venue
                    Venue venue = venueService.findById(screening.getVenueId());
                    if (venue != null) {
                        screeningData.put("venue", venue);
                    }

                    enrichedReservation.put("screening", screeningData);
                }

                enrichedReservations.add(enrichedReservation);
            }

            return ResponseEntity.ok(enrichedReservations);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching reservations: " + e.getMessage());
        }
    }

    @PostMapping("/{screening}")
    public ResponseEntity<?> createReservation(@PathVariable Long screening, @RequestBody Reservation reservation) {
        if (!userSessionService.loggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión activa");
        }

        User currentUser = userSessionService.getCurrentUser();

        // Asignar el usuario actual a la reserva
        reservation.setUserId(currentUser.getId());

        // Verificar que el screening ID coincida
        if (!screening.equals(reservation.getScreeningId())) {
            return ResponseEntity.badRequest().body("ID de screening no coincide");
        }

        try {
            int savedReservation = reservationService.reserveSeats(reservation);
            if (savedReservation == 1) {
                return ResponseEntity.ok(reservation);
            }
            return ResponseEntity.badRequest().body("No se pudo crear la reserva");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la reserva: " + e.getMessage());
        }
    }

    @DeleteMapping("/cancel/{reservation_id}")
    public ResponseEntity<?> cancelReservation(@PathVariable long reservation_id) {
        Reservation reservation = reservationService.findById(reservation_id);

        if (!userSessionService.loggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        if (reservation.getUserId() != (userSessionService.getCurrentUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        int cancelation = reservationService.cancelReservedSeats(reservation_id);
        if (cancelation == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}