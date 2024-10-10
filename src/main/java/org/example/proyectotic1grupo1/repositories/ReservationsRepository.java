package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {

    public List<List<Integer>> ocupationQuery(int venueNumber, Date date, int movie_key);
    public void reserveQuery(int venueNumber, Date date, int movie_key,String mail,int row,int col);
    public void cancelationQuery(int venueNumber, Date date, int movie_key,String mail);
}
