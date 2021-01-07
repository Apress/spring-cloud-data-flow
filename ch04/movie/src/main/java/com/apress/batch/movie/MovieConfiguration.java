package com.apress.batch.movie;

import org.h2.tools.Server;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
@ImportResource("META-INF/spring/movie-batch-context.xml")
public class MovieConfiguration {

   @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-ifNotExists","-tcpAllowOthers", "-tcpPort", "9092");
    }

    // Web - http://localhost:8092 - URL: jdbc:h2:~/movies;AUTO_SERVER=TRUE
    /*
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-ifNotExists","-webAllowOthers", "-webPort", "8092");
    }
    */
}
