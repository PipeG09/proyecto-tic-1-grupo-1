package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.controllers.ScreeningController;
import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScreeningControllerTest {

    @Mock
    private ScreeningService screeningService;

    @InjectMocks
    private ScreeningController screeningController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllScreenings() {
        Screening screening1 = new Screening(1L, 2L, LocalDateTime.now());
        Screening screening2 = new Screening(2L, 3L, LocalDateTime.now());

        when(screeningService.findAll()).thenReturn(Arrays.asList(screening1, screening2));

        List<Screening> response = screeningController.getAllScreenings().getBody();

        assertEquals(2, response.size());
        assertEquals(screening1, response.get(0));
        verify(screeningService, times(1)).findAll();
    }

    @Test
    void testGetScreeningById() throws Exception {
        Screening screening = new Screening(1L, 2L, LocalDateTime.now());
        when(screeningService.findById(1L)).thenReturn(screening);

        Screening response = screeningController.getScreeningById(1L).getBody();

        assertEquals(screening, response);
        verify(screeningService, times(1)).findById(1L);
    }

    @Test
    void testCreateScreening() {
        Screening screening = new Screening(1L, 2L, LocalDateTime.now());
        when(screeningService.save(screening)).thenReturn(screening);

        Screening response = screeningController.createScreening(screening).getBody();

        assertEquals(screening, response);
        verify(screeningService, times(1)).save(screening);
    }

    @Test
    void testDeleteScreening() throws Exception {
        ResponseEntity<Void> response = screeningController.deleteScreening(1L);

        assertEquals("Screening deleted successfully", response.getBody());
        verify(screeningService, times(1)).deleteById(1L);
    }
}
