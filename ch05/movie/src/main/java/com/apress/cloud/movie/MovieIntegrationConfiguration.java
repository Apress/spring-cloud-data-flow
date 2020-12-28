package com.apress.cloud.movie;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.web.client.RestTemplate;

import java.io.File;

// Version 1
/*
@AllArgsConstructor
@EnableConfigurationProperties(MovieProperties.class)
@Configuration
public class MovieIntegrationConfiguration {

    private MovieProperties movieProperties;
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieProperties.getFilePattern()),
                e -> e.poller(Pollers.fixedDelay(this.movieProperties.getFixedDelay())))


                .split(Files.splitter().markers())
                //.log(LoggingHandler.Level.INFO,"Marker", m -> m)
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.converter(this.movieConverter))
                .transform(Transformers.toJson())
                //.log(LoggingHandler.Level.INFO,"JSON", m -> m.getPayload())
                //.log(LoggingHandler.Level.INFO,"JSON", m -> m.getHeaders())
                .handle(Http
                        .outboundChannelAdapter(URI.create(this.movieProperties.getRemoteService()))
                        .httpMethod(HttpMethod.POST))
                .get();
    }
}
*/


// Version 2 - Eureka Client
/*
@AllArgsConstructor
@EnableConfigurationProperties(MovieProperties.class)
@Configuration
public class MovieIntegrationConfiguration {

    private MovieProperties movieProperties;
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieProperties.getFilePattern()),
                e -> e.poller(Pollers.fixedDelay(this.movieProperties.getFixedDelay())))
                .split(Files.splitter().markers())
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.converter(this.movieConverter))
                .handle("movieHandler", "process")
                .get();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public MovieHandler movieHandler(EurekaClient discoveryClient){
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("MOVIE-WEB", false);
        return new MovieHandler(restTemplate(),instance.getHomePageUrl() + "v1/movie");
    }

}
*/

// Version 3 - With Load Balancer - Ribbon
/*
@AllArgsConstructor
@EnableConfigurationProperties(MovieProperties.class)
@Configuration
public class MovieIntegrationConfiguration {

    private MovieProperties movieProperties;
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieProperties.getFilePattern()),
                e -> e.poller(Pollers.fixedDelay(this.movieProperties.getFixedDelay())))
                .split(Files.splitter().markers())
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.converter(this.movieConverter))
                .handle("movieHandler", "process")
                .get();
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public MovieHandler movieHandler(){
        return new MovieHandler(restTemplate());
    }
}
*/

// Version 4 - With Circuit Breakers
@EnableCircuitBreaker
@AllArgsConstructor
@EnableConfigurationProperties(MovieProperties.class)
@Configuration
public class MovieIntegrationConfiguration {

    private MovieProperties movieProperties;
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlows.from(Files
                        .inboundAdapter(new File(this.movieProperties.getDirectory()))
                        .preventDuplicates(true)
                        .patternFilter(this.movieProperties.getFilePattern()),
                e -> e.poller(Pollers.fixedDelay(this.movieProperties.getFixedDelay())))
                .split(Files.splitter().markers())
                .filter(p -> !(p instanceof FileSplitter.FileMarker))
                .transform(Transformers.converter(this.movieConverter))
                .handle("movieHandler", "process")
                .get();
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public MovieHandler movieHandler(){
        return new MovieHandler(restTemplate());
    }
}