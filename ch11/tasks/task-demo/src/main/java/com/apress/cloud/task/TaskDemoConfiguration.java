package com.apress.cloud.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Log4j2
@EnableTask
@Configuration
public class TaskDemoConfiguration {

    private final String MOVIES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS movies(" +
            " id varchar(10) primary key," +
            " title varchar(200)," +
            " actor varchar(200)," +
            " year int," +
            " genre varchar(25)," +
            " stars int," +
            " rating decimal(2,1)," +
            " ratingcount int);";

    private final String MOVIES_INSERT_SQL_1 = "insert into movies (id,title,actor,year,genre,stars,rating,ratingcount) " +
            "values ('tt0133093','The Matrix','Keanu Reeves',1999,'fiction',5,8.7,1605968);";

    private final String MOVIES_INSERT_SQL_2 = "insert into movies (id,title,actor,year,genre,stars,rating,ratingcount) " +
            "values ('tt0209144','Memento','Guy Pearce',2000,'drama',4,8.4,1090922);";

    private final String MOVIES_INSERT_SQL_ERROR = "insert into movies (year,genre,stars,rating,ratingcount) " +
            "values ('tt0209144','Memento','Guy Pearce',2000,'drama',4,8.4,1090922);";

    @Bean
    public CommandLineRunner process(DataSource dataSource){
        return args -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute(MOVIES_TABLE_SQL);
            jdbcTemplate.execute(MOVIES_INSERT_SQL_1);
            jdbcTemplate.execute(MOVIES_INSERT_SQL_2);
            //jdbcTemplate.execute(MOVIES_INSERT_SQL_ERROR);
        };
    }

    @Bean
    public TaskDemoListener taskDemoListener(){
        return new TaskDemoListener();
    }
}
