package org.example.proyectotic1grupo1.repositories;

import java.util.Date;
import java.util.List;

public interface ReservationsRepository {

    public List<List<Integer>> ocupationQuery(int venueNumber, Date date, int movie_key);
    public void reserveQuery(int venueNumber, Date date, int movie_key,String mail,int row,int col);
    public void cancelationQuery(int venueNumber, Date date, int movie_key,String mail);
}
