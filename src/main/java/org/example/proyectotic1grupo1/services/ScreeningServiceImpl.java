package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.repositories.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Screening> findAll() {
        return screeningRepository.findAll();
    }

    @Override
    public Screening findById(Long id) throws Exception {
        return screeningRepository.findById(id).orElseThrow(() -> new Exception("Screening not found"));
    }

    @Override
    public Screening save(Screening screening) {
        return screeningRepository.save(screening);
    }

    @Override
    public Screening update(Long id, Screening updatedScreening) throws Exception {
        Screening existingScreening = screeningRepository.findById(id)
                .orElseThrow(() -> new Exception("Screening not found"));

        // Actualizar los campos necesarios
        existingScreening.setDate(updatedScreening.getDate());
        existingScreening.setMovieId(updatedScreening.getMovieId());
        existingScreening.setVenueId(updatedScreening.getVenueId());

        return screeningRepository.save(existingScreening);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new Exception("Screening not found"));

        screeningRepository.delete(screening);
    }
}
