package com.apress.cloud.stream.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieFilterProcessorNatsRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieFilterProcessorNatsRabbitApplication.class, args);
	}

}
