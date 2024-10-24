package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.Venue;

import java.util.List;

public interface VenueService {
    List<Venue> findAll();
    Venue findById(Long id) throws Exception;
    Venue save(Venue venue);
    void deleteById(Long id) throws Exception;
}
