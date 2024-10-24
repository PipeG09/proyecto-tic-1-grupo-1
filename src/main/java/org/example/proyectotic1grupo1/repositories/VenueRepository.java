package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}
