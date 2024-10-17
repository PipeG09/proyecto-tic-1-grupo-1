package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {
    @Query("")
    public List<Reservation> ocupationQuery(int venueNumber, Date date, int movie_key);
    @Query("")
    public void reserveQuery(int venueNumber, Date date, int movie_key,String mail,int row,int col);
}
