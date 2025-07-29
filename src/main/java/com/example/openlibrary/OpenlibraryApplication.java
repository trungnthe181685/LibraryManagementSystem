package com.example.openlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OpenlibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenlibraryApplication.class, args);
	}

}
