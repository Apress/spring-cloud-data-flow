package com.apress.cloud.stream.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieSourceKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieSourceKafkaApplication.class, args);
	}

}
