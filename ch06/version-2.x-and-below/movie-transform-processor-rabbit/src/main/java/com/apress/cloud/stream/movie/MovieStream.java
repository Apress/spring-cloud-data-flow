package com.apress.cloud.stream.movie;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Processor.class)
public class MovieStream {


    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Movie process(Movie movie){
        movie.setTitle(movie.getTitle().toUpperCase());
        return movie;
    }


    // Another ways to consume Messages from the Processor.INPUT and produce using Processor.OUTPUT
    /*
    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Movie transform(Movie movie){
        movie.setTitle(movie.getTitle().toUpperCase());
        return movie;
    }
    */

    /*
    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Movie transformServiceActivator(Movie movie){
        movie.setTitle(movie.getTitle().toUpperCase());
        return movie;
    }
    */
}



// # Add the following property to the application.properties file:
// # ONLY: Binding when using Spring Cloud Function - Function
// spring.cloud.stream.bindings.uppercase-in-0.destination=movie
// spring.cloud.stream.bindings.uppercase-out-0.destination=log
/*
@Log4j2
@Configuration
public class MovieStream {

    @Bean
    public Function<Movie, Movie> uppercase() {
        return movie -> {
            log.info("Processing: {}", movie);
            movie.setTitle(movie.getTitle().toUpperCase());
            return movie;
        };
    }
}
*/