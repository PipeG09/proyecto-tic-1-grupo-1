package org.example.proyectotic1grupo1.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.ReservationsRepository;
import org.springframework.stereotype.Service;



@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationsRepository reservationsRepository;

    public ReservationServiceImpl(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    @Override
    public List<List<Integer>> ocupationMatrix(Screening screening){
        // Inicialize the ocupation matrix
        List<List<Integer>> ocupation_matrix = new ArrayList<>();
        for (int i = 0; i<10; i++){
            List<Integer> fila = new ArrayList<>();
            for (int j = 0; j<15;j++){
                fila.add(0);
            }
            ocupation_matrix.add(fila);
        }

        // Fill it
            List<Reservation> occupied_seats = reservationsRepository.findAllByScreening(screening);

        // Thought of adding a try catch, but the function is for internal use so i assume it will always be called
        // with valid parameters

        for (Reservation occupiedSeat : occupied_seats) { // iterate through the reservations
            ocupation_matrix.get(occupiedSeat.getSeatRow()).set(occupiedSeat.getSeatColumn(), 1);
            // I change the row corresponding to the seatRow and set the seatColumn to 1
        }


        return ocupation_matrix;
    }


    @Override
    public int reserveSeats(Reservation reservation){
        Reservation res = this.findById(reservation.getReservationId());
        if (res == null) {
            try {
                reservationsRepository.save(reservation);
                return 1;
            } catch (Exception e) {
                return -1;
            }
        }
        else{
            return -1;
        }

    }

    @Override
    public int cancelReservedSeats(long reservationId){
        if (reservationsRepository.existsById(reservationId)) {
            try {
                reservationsRepository.deleteById(reservationId);
            } catch (Exception e){return -1;}
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public Reservation findById(long id){
        return reservationsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findAll(User user) {
        LocalDateTime date = LocalDateTime.now().minusHours(1);
        return reservationsRepository.findByUserIdAndScreening_DateAfter(user.getId(),date);
    }
}
