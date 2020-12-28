package com.apress.cloud.stream.movie;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Log4j2
@Configuration
public class MovieStream {
    @Bean
    public Consumer<Flux<Movie>> log() {
        return movie -> movie.subscribe(actual -> log.info(actual));
    }
}
