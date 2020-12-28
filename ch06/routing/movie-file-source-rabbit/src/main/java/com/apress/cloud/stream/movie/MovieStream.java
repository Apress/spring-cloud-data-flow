package com.apress.cloud.stream.movie;

import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.function.Supplier;

@EnableConfigurationProperties(MovieStreamProperties.class)
@AllArgsConstructor
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
                .enrichHeaders(h -> h.headerExpression("genre","payload.genre"))
                .toReactivePublisher();

    }

    @Bean
    public Supplier<Publisher<Message<Movie>>> movie(){
        return () -> fileFlow();
    }


    /* An example of how to Read a files using Flux
    @Bean
    public Supplier<Flux<String>> source() {
        return () -> Flux.using(()-> Files.lines(Paths.get(movieStreamProperties.getDirectory() + "/" + movieStreamProperties.getNamePattern())),Flux::fromStream, BaseStream::close);
    }
    */
}
