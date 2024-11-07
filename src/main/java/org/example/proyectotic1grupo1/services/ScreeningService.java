package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.Venue;

import java.util.List;

public interface ScreeningService {
    List<Screening> findAll();
    Screening findById(Long id) throws Exception;
    Screening save(Screening screening);
    Screening update(Long id, Screening updatedScreening) throws Exception;
    void deleteById(Long id) throws Exception;
    List<Screening> findByVenueId(long venueId);
    List<Venue> findVenueByMovieId(long movieId);
    List<Screening> findByVenueMovie(long venueId,long movieId);
}
