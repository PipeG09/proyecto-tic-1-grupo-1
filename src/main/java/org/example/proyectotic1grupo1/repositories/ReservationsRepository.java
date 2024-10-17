package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByScreening(Screening screening);
    List<Reservation> findByUserIdAndScreening_DateAfter(Long userId, LocalDateTime dateTime);
}
