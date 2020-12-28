package com.apress.cloud.stream.movie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Configuration
public class MovieStream {

    @Bean
    public Supplier<Flux<Movie>> movie() {
        return () -> Flux.just(
                new Movie("The Matrix","Keanu Reves",1999,"science-fiction"),
                new Movie("It","Bill Skarsgård",2017,"horror")
        );
    }

    /*
    @Bean  // Every second will send this movie.
    public Supplier<Movie> movie() {
        return () -> new Movie("The Matrix","Keanu Reves",1999,"science-fiction");
    }
    */

    /*
    @PollableBean
    public Supplier<Flux<Movie>> movie() {
        return () -> Flux.just(
                new Movie("The Matrix","Keanu Reves",1999,"science-fiction"),
                new Movie("It","Bill Skarsgård",2017,"horror")
                );
    }
    */
}
