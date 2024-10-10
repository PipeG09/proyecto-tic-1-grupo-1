package org.example.proyectotic1grupo1.services;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.example.proyectotic1grupo1.repositories.ReservationsRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservetionService {
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
            List<List<Integer>> occupied_seats = reservationsRepository.ocupationQuery(venueNumber, date, movie_key);
        // Thought of adding a try catch, but the function is for internal use so i assume it will always be called
        // with valid parameters

        for (int i = 0; i < occupied_seats.size(); i++){
            for (int j=0; j<2; j++){
                ocupation_matrix.get(i).set(j,1);
            }
        }

        return ocupation_matrix;
    }


    public int reserve_seats(int venueNumber, Date date, int movie_key,String mail,int row,int col){
        try{
            reservationsRepository.reserveQuery(venueNumber,date,movie_key,mail,row,col);
            return 1;
        }catch(Exception e){
            return -1;
        }

    }
    public int cancel_reserved_seats(int venueNumber, Date date, int movie_key,String mail){
        try {
            reservationsRepository.cancelationQuery(venueNumber,date,movie_key,mail);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }



}
