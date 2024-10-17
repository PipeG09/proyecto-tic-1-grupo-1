package org.example.proyectotic1grupo1.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.repositories.ReservationsRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;



@Service
public class ReservationService {
    private ReservationsRepository reservationsRepository;
    public List<List<Integer>> ocupation_matrix(int venueNumber, Date date, int movie_key){
        // Inicialise the ocupation matrix
        List<List<Integer>> ocupation_matrix = new ArrayList<>();
        for (int i = 0; i<10; i++){
            List<Integer> fila = new ArrayList<>();
            for (int j = 0; j<15;j++){
                fila.add(0);
            }
            ocupation_matrix.add(fila);
        }

        // Fill it
            List<Reservation> occupied_seats = reservationsRepository.ocupationQuery(venueNumber, date, movie_key);

        // Thought of adding a try catch, but the function is for internal use so i assume it will always be called
        // with valid parameters

        for (Reservation occupiedSeat : occupied_seats) { // iterate through the reservations
            ocupation_matrix.get(occupiedSeat.getSeatRow()).set(occupiedSeat.getSeatColumn(), 1);
            // I change the row corresponding to the seatRow and set the seatColumn to 1
        }


        return ocupation_matrix;
    }


    public int reserve_seats(long screeningId,long userId,long venueId,int seatRow,int seatColumn){

        Reservation reservation = new Reservation(screeningId, venueId, userId, seatRow, seatColumn);
        try {reservationsRepository.save(reservation);
            return 1;
        }
        catch(Exception e){
            return -1;
        }

    }
    public int cancel_reserved_seats(long reservationId){
        if (reservationsRepository.existsById(reservationId)) {
            try {
                reservationsRepository.deleteById(reservationId);
            } catch (Exception e){return -1;}
            return 1;
        } else {
            return -1;
        }
    }



}
