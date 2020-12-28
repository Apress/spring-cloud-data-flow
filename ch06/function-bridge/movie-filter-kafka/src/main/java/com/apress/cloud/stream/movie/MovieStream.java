package com.apress.cloud.stream.movie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;


@Configuration
public class MovieStream {
    String GENRE_SCIENCE_FICTION = "science-fiction";

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> onlyScienceFiction() {
        return flux -> flux.filter( movie -> movie.getGenre().equals(GENRE_SCIENCE_FICTION));
    }
}



//Using Function Composition
//You need to enable the spring.cloud.function.definition property to have one or more functions to create a composition
/*
@Configuration
public class MovieStream {
    String GENRE_SCIENCE_FICTION = "science-fiction";

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> onlyScienceFiction() {
        return flux -> flux.filter( movie -> movie.getGenre().equals(GENRE_SCIENCE_FICTION));
    }

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> titleUpperCase() {
        return flux -> flux.map( movie -> {
            movie.setTitle(movie.getTitle().toUpperCase());
            return movie;
        });
    }
}
*/



//Example with multiple input arguments
/*
@Configuration
public class MovieStream {
    String GENRE_SCIENCE_FICTION = "science-fiction";

    @Bean
    public Function<Tuple2<Flux<Integer>,Flux<Movie>>, Flux<Message<Movie>>> movieTuple() {
        return tuple -> {
            Flux<Integer> integerFlux = tuple.getT1();
            Flux<Movie> movieFlux = tuple.getT2();

            return Flux.just(
                    MessageBuilder.withPayload(movieFlux.blockFirst()).setHeader("stars",integerFlux.map(m -> m.toString())).build());
        };
    }
}
*/



// When going from Reactive to Spring Integration.
/*
@Configuration
public class MovieStream {
    @Bean
    public IntegrationFlow movieFlow() {
        return IntegrationFlows.from(MovieFunction.class)
                .transform(Transformers.toJson())
                .channel(Source.OUTPUT)
                .get();
    }
    public interface MovieFunction extends Function<Movie, Movie> { }
}
*/