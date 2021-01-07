package com.apress.cloud.stream.movie;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.SpelFunctionFactoryBean;
import org.springframework.integration.json.JsonPathUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
public class MovieConfiguration {
    @Bean
    public SpelFunctionFactoryBean jsonPath() {
        return new SpelFunctionFactoryBean(JsonPathUtils.class, "evaluate");
    }

    // Uncomment to send message to the movie channel.
    /*
    @Bean
    public ApplicationRunner sendMovies(MovieGenre movieGenre){
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();

            Movie theMatrix = new Movie("The Matrix","Keanu Reves",1999,"science-fiction");
            Movie it =  new Movie("It","Bill Skarsgård",2017,"horror");

            movieGenre.movie().send(
                    MessageBuilder
                            .withPayload(objectMapper.writeValueAsString(theMatrix))
                            .setHeader("content_type","application/json")
                            .build());

            movieGenre.movie().send(
                    MessageBuilder
                            .withPayload(objectMapper.writeValueAsString(it))
                            .setHeader("content_type","application/json")
                            .build());
        };
    }
    */
}
