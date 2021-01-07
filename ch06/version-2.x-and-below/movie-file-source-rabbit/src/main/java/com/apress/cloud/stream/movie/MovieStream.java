package com.apress.cloud.stream.movie;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;

import java.io.File;


@EnableConfigurationProperties(MovieStreamProperties.class)
@AllArgsConstructor
@EnableBinding(Source.class)
public class MovieStream {

    private MovieStreamProperties movieStreamProperties;
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow(){
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieStreamProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieStreamProperties.getNamePattern()),
                e -> e.poller(Pollers.fixedDelay(5000L)))

                .split(Files.splitter().markers())
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.converter(movieConverter))
                .transform(Transformers.toJson())
                .channel(Source.OUTPUT)
                .get();
    }


    // Another ways to send Messages to the Source.OUTPUT
    /*
    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "1"))
    public MessageSource<Movie> movieMessageSource() {
        return () -> new GenericMessage<>(new Movie("The Matrix","Keanu Reeves",1999));
    }
    */

    /*
    @Bean
    public ApplicationRunner movieMessage(Source source){
        return args -> {
            source.output().send(new GenericMessage<>(new Movie("The Matrix","Keanu Reeves",1999)));
        };
    }
    */
}


/*
// # Add the following property to the application.properties file:
// # ONLY: Binding when using Spring Cloud Function - Suplier
// spring.cloud.stream.bindings.movieSupplier-out-0.destination=movie

@Configuration
public class MovieStream {
    @Bean  // Every second will send this movie.
    public Supplier<Movie> movieSupplier() {
        return () -> new Movie("The Matrix", "Keanu Reves", 1999);
    }
}
*/
