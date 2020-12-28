package com.apress.integration.movieweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;


@MessageEndpoint
public class MovieEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MovieEndpoint.class);

    @ServiceActivator
    public void processMovie(Movie movie, @Headers Map<String,Object> headers) throws Exception {
        log.info("Movie: " + movie);
    }


    @ServiceActivator
    public void processMovies(Movie[] movies, @Headers Map<String,Object> headers) throws Exception {
        for (Movie movie: movies){
            log.info("Movie: " +  movie);
        }
    }
}
