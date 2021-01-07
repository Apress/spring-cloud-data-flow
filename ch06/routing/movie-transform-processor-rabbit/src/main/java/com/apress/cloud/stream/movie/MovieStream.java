package com.apress.cloud.stream.movie;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Log4j2
@Configuration
public class MovieStream {

    @Bean
    public Function<Movie, Movie> drama() {
        return movie -> {
            log.info("Drama: {}",movie);
            movie.setGenre(movie.getGenre().toUpperCase());
            return movie;
        };
    }

    @Bean
    public Function<Movie, Movie> fiction() {
        return movie -> {
            log.info("Science Fiction: {}", movie);
            movie.setTitle(movie.getTitle().toUpperCase());
            return movie;
        };
    }
}
