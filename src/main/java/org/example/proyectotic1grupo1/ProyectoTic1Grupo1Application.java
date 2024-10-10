package org.example.proyectotic1grupo1;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoTic1Grupo1Application {

	public static void main(String[] args) {
		// Carga las variables del archivo .env
		Dotenv dotenv = Dotenv.configure().load();

		// Establece las variables de entorno para que Spring Boot las reconozca
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		SpringApplication.run(ProyectoTic1Grupo1Application.class, args);
	}

}
