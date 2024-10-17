package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.User;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    List<List<Integer>> ocupationMatrix(Screening screening);
    int reserveSeats(Reservation reservation);

    int cancelReservedSeats(long reservationId);

    Reservation findById(long id);

    List<Reservation> findAll(User user);
}
