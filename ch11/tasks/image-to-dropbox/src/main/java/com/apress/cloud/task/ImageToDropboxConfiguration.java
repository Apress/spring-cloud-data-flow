package com.apress.cloud.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Log4j2
@EnableTask
@Configuration
@EnableConfigurationProperties({ImageToDropboxProperties.class})
public class ImageToDropboxConfiguration {

    @Bean
    public ImageToDropboxUtils imageToDropBoxUtils(ImageToDropboxProperties imageToDropboxProperties){
        return new ImageToDropboxUtils(imageToDropboxProperties);
    }

    private final String MOVIES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS art(" +
            " id varchar(10) primary key," +
            " url varchar(500));";

    private final String MOVIES_INSERT_SQL_1 = "insert into art (id,url) " +
            "values ('tt0133093','https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg');";
    private final String MOVIES_INSERT_SQL_2 = "insert into art (id,url) " +
            "values ('tt0209144','https://m.media-amazon.com/images/M/MV5BZTcyNjk1MjgtOWI3Mi00YzQwLWI5MTktMzY4ZmI2NDAyNzYzXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg');";

    private final String MOVIES_QUERY_SQL = "select url from art where id=?;";

    private final String MATRIX_ART_ID = "tt0133093";
    private final String MEMENTO_ART_ID = "tt0209144";

    @Bean
    public CommandLineRunner process(DataSource dataSource, ImageToDropboxUtils imageToDropBoxUtils){
        return args -> {
            log.debug("Connecting to: {} ", dataSource.getConnection().getMetaData().getURL());

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute(MOVIES_TABLE_SQL);
            jdbcTemplate.execute(MOVIES_INSERT_SQL_1);
            jdbcTemplate.execute(MOVIES_INSERT_SQL_2);

            String url = null;

            url = jdbcTemplate.queryForObject(
                    MOVIES_QUERY_SQL, new Object[]{MATRIX_ART_ID}, String.class);
            log.debug("URL: {}", url);
            imageToDropBoxUtils.fromUrlToDropBox(url,MATRIX_ART_ID + ".jpg");

            url = jdbcTemplate.queryForObject(
                    MOVIES_QUERY_SQL, new Object[]{MEMENTO_ART_ID}, String.class);
            log.debug("URL: {}", url);
            imageToDropBoxUtils.fromUrlToDropBox(url,MEMENTO_ART_ID + ".jpg");
        };
    };

}
