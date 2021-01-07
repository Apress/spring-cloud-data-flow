package com.apress.cloud.movie;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

// Version 1 - With Eureka Client
/*
@Slf4j
@AllArgsConstructor
public class MovieHandler {

    private RestTemplate restTemplate;
    private String serviceUrl;

    public void process(Movie movie){
        log.debug("Processing: {}", movie);
        log.debug("ServiceURL: {}", serviceUrl);

        ResponseEntity<Object> response = this.restTemplate.postForEntity(serviceUrl,movie,Object.class);
        if(response.getStatusCode().equals(HttpStatus.OK))
            log.debug("processed");
        else
            log.warn("Take a look of the logs...");
    }
}
*/

// Version 2 - With Ribbon
/*
@Slf4j
@AllArgsConstructor
public class MovieHandler {

    private RestTemplate restTemplate;

    public void process(Movie movie){
        log.debug("Processing: {}", movie);
        ResponseEntity<Object> response = this.restTemplate.postForEntity("http://movie-web/v1/movie",movie,Object.class);
        if(response.getStatusCode().equals(HttpStatus.OK))
            log.debug("processed");
        else
            log.warn("Take a look of the logs...");
    }
}
*/

// Version 3 - With Circuit Breakers

@Slf4j
@AllArgsConstructor
public class MovieHandler {

    private RestTemplate restTemplate;

    public void process(Movie movie){
        log.debug("Processing: {}", movie);
        if(postMovie(movie))
            log.info("PROCESSED!");
    }

    @HystrixCommand(fallbackMethod = "defaultProcess")
    public boolean postMovie(Movie movie){
        ResponseEntity<Object> response = this.restTemplate.postForEntity("http://movie-web/v1/movie",movie,Object.class);
        if(response.getStatusCode().equals(HttpStatus.OK))
            return true;
        return false;
    }

    public boolean defaultProcess(Movie movie){
        log.error("COULD NOT process: {}, please try later.", movie);
        return false;
    }
}