package com.apress.integration.movieweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @RequestMapping(method=RequestMethod.POST,value="/v1/movie")
    public ResponseEntity<String> movie(@RequestBody Movie body){
        log.info("Movie: " +  body);
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(method=RequestMethod.POST,value="/v1/movies")
    public ResponseEntity<String> movies(@RequestBody Movie[] body){
        for (Movie movie: body){
            log.info("Movie: " +  movie);
        }
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

}
