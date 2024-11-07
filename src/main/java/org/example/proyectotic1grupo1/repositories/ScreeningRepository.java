package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
        List<Screening> findByVenueIdAndDateAfter(long venueId,LocalDateTime dateTime);

        @Query("SELECT DISTINCT s.venue FROM Screening s " +
                "WHERE s.movieId = :movieId AND s.date > :timeAfter")
        List<Venue> findDistinctVenuesByMovieIdAndTimeAfter(
                @Param("movieId") Long movieId,
                @Param("timeAfter") LocalDateTime timeAfter);

        List<Screening> findByVenueIdAndMovieIdAndDateAfter(Long venueId, Long movieId, LocalDateTime date);

}
