package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.models.Reservation;
import org.example.proyectotic1grupo1.models.Screening;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.ReservationsRepository;
import org.example.proyectotic1grupo1.services.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTests {

    @Mock
    private ReservationsRepository reservationsRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Screening screening;
    private Reservation reservation;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        screening = new Screening();
        screening.setScreeningId(1L);
        screening.setDate(LocalDateTime.now());

        reservation = new Reservation();
        reservation.setScreeningId(1L);
        reservation.setSeatRow(2);
        reservation.setSeatColumn(3);
        reservation.setScreening(screening);

        user = new User();
        user.setId(1L);
    }

    @Test
    void testOcupationMatrix() {
        // Arrange
        List<Reservation> occupiedSeats = Arrays.asList(reservation);
        when(reservationsRepository.findAllByScreeningId(screening.getScreeningId())).thenReturn(occupiedSeats);

        // Act
        List<List<Integer>> result = reservationService.ocupationMatrix(screening);

        // Assert
        assertEquals(1, result.get(2).get(3)); // Check that seat 2,3 is marked as occupied
    }

    @Test
    void testReserveSeats_Success() {
        // Arrange
        when(reservationsRepository.save(reservation)).thenReturn(reservation);

        // Act
        int result = reservationService.reserveSeats(reservation);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testReserveSeats_Failure() {
        // Arrange
        doThrow(new RuntimeException("Error while saving")).when(reservationsRepository).save(reservation);

        // Act
        int result = reservationService.reserveSeats(reservation);

        // Assert
        assertEquals(-1, result);
    }

    @Test
    void testCancelReservedSeats_Success() {
        // Arrange
        when(reservationsRepository.existsById(1L)).thenReturn(true);

        // Act
        int result = reservationService.cancelReservedSeats(1L);

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testCancelReservedSeats_NotFound() {
        // Arrange
        when(reservationsRepository.existsById(1L)).thenReturn(false);

        // Act
        int result = reservationService.cancelReservedSeats(1L);

        // Assert
        assertEquals(-1, result);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(reservationsRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getScreeningId());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(reservationsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Reservation result = reservationService.findById(1L);

        // Assert
        assertNull(result);
    }

}

