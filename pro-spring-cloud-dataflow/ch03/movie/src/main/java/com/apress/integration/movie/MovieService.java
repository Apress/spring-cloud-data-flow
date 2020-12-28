package com.apress.integration.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    public String format(String contents){
        log.info("Formatting to Json...");

        String json = "{}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(parse(contents));
            log.info("\n" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private List<Movie> parse(String contents){
        List<Movie> movies = new ArrayList<Movie>();
        String[] record = null;
        for(String line: contents.split(System.getProperty("line.separator"))){
            record = Arrays.asList(line.split(",")).stream().map( c -> c.trim()).toArray( size -> new String[size]);
            movies.add(new Movie(record[0],record[1],Integer.valueOf(record[2])));
        }
        return movies;
    }
}
