package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.controllers.VenueController;
import org.example.proyectotic1grupo1.models.Venue;
import org.example.proyectotic1grupo1.services.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VenueServiceTests {

    @Mock
    private VenueService venueService;

    @InjectMocks
    private VenueController venueController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVenues() {
        Venue venue1 = new Venue("Neighbourhood1");
        Venue venue2 = new Venue("Neighbourhood2");

        when(venueService.findAll ()).thenReturn(Arrays.asList(venue1, venue2));

        List<Venue> response = venueController.getAllVenues().getBody();

        assertEquals(2, response.size());
        assertEquals(venue1, response.get(0));
        verify(venueService, times(1)).findAll();
    }

    @Test
    void testGetVenueById() throws Exception {
        Venue venue = new Venue("Neighbourhood1");
        when(venueService.findById(1L)).thenReturn(venue);

        Venue response = venueController.getVenueById(1L).getBody();

        assertEquals(venue, response);
        verify(venueService, times(1)).findById(1L);
    }



}
