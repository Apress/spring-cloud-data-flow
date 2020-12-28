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

@AllArgsConstructor
@EnableConfigurationProperties(MovieStreamProperties.class)
@EnableBinding(Source.class)
public class MovieStream {

    private MovieStreamProperties movieStreamProperties;

    @Bean
    public IntegrationFlow fileFlow(){
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieStreamProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieStreamProperties.getNamePattern()),
                        e -> e.poller(Pollers.fixedDelay(5000L)))
                .split(Files.splitter().markers())
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.fromJson(Movie.class))
                .channel(Source.OUTPUT)
                .get();
    }
}


// Programming Model 3.x
// Yout need to have this property declared:
// spring.cloud.stream.bindings.movie-out-0.destination=movie
//

/*
@AllArgsConstructor
@EnableConfigurationProperties(MovieStreamProperties.class)
@Configuration
public class MovieStream {

    private MovieStreamProperties movieStreamProperties;

    @Bean
    public Publisher<Message<Movie>> fileFlow(){
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieStreamProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieStreamProperties.getNamePattern()),
                e -> e.poller(Pollers.fixedDelay(5000L)))
                .split(Files.splitter().markers())
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.fromJson(Movie.class))
                .toReactivePublisher();
    }

    @Bean
    public Supplier<Publisher<Message<Movie>>> movie(){
        return () -> fileFlow();
    }
}
*/