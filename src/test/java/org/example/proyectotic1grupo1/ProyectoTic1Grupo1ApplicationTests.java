package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.services.MovieService;
import org.example.proyectotic1grupo1.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProyectoTic1Grupo1ApplicationTests {

	@Autowired
	private MovieService movieService;

	@Autowired
	private ReservationService reservationService;

	@Test
	void contextLoads() {
		// Verifica que los servicios no sean nulos
		assertThat(movieService).isNotNull();
		assertThat(reservationService).isNotNull();
	}

}
