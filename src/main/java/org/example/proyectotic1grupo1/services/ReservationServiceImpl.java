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
    public List<List<Integer>> ocupationMatrix(Screening screening) {
        // Inicializar la matriz de ocupación (15 filas x 10 columnas)
        List<List<Integer>> ocupation_matrix = new ArrayList<>();
        // Crear 15 filas
        for (int i = 0; i < 15; i++) {
            List<Integer> fila = new ArrayList<>();
            // Cada fila tiene 10 asientos
            for (int j = 0; j < 10; j++) {
                fila.add(0);
            }
            ocupation_matrix.add(fila);
        }

        // Obtener las reservas existentes
        List<Reservation> occupied_seats = reservationsRepository.findAllByScreeningId(screening.getScreeningId());

        // Marcar los asientos ocupados
        for (Reservation occupiedSeat : occupied_seats) {
            int row = occupiedSeat.getSeatRow();
            int col = occupiedSeat.getSeatColumn();
            // Verificar que los índices estén dentro de los límites
            if (row >= 0 && row < 15 && col >= 0 && col < 10) {
                ocupation_matrix.get(row).set(col, 1);
            }
        }

        return ocupation_matrix;
    }


    @Override
    public int reserveSeats(Reservation reservation){
        Reservation res = this.findRes(reservation);
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

    public  Reservation findRes(Reservation reservation){

        return reservationsRepository.findByScreeningIdAndSeatRowAndSeatColumn(reservation.getScreeningId(),reservation.getSeatRow(),reservation.getSeatColumn()).orElse(null);
    }
}
