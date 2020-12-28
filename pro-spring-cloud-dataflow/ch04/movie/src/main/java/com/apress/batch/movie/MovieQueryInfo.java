package com.apress.batch.movie;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MovieQueryInfo {

    @Bean
    public CommandLineRunner getInfo(JdbcTemplate jdbcTemplate){
        return args -> {
            jdbcTemplate.query(
                    "select title,actor,year from movies",
                    (rs, rowNum) ->
                            new Movie(
                                    rs.getString("title"),
                                    rs.getString("actor"),
                                    rs.getInt("year")
                            )
            ).forEach(System.out::println);
        };
    }
}
