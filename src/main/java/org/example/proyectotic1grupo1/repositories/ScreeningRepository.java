package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

}
