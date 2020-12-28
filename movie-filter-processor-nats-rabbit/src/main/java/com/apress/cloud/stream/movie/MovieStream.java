package com.apress.cloud.stream.movie;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Filter;

@EnableBinding(Processor.class)
public class MovieStream {

    String GENRE_DRAMA = "drama";

    @Filter(inputChannel = Processor.INPUT,outputChannel = Processor.OUTPUT)
    public boolean onlyDrama(Movie movie) {
        return movie.getGenre().equals(GENRE_DRAMA);
    }

}


// Programming Model 3.x
/*
@Configuration
public class MovieStream {

    String GENRE_DRAMA = "drama";

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> onlyDrama() {
        return flux -> flux.filter( movie -> movie.getGenre().equals(GENRE_DRAMA));
    }

}
*/