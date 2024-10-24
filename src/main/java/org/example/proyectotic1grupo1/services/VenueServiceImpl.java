package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.Venue;
import org.example.proyectotic1grupo1.repositories.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    public VenueServiceImpl(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    @Override
    public Venue findById(Long id) throws Exception {
        return venueRepository.findById(id)
                .orElseThrow(() -> new Exception("Venue not found"));
    }

    @Override
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new Exception("Venue not found"));

        venueRepository.delete(venue);
    }
}

