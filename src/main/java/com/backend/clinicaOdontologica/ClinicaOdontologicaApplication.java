package com.backend.clinicaOdontologica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ClinicaOdontologicaApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClinicaOdontologicaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontologicaApplication.class, args);
		LOGGER.info("Clinica Odontologica is now running ...");
	}

}
