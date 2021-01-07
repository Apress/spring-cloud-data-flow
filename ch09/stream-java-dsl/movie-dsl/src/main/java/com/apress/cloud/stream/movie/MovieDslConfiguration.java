package com.apress.cloud.stream.movie;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.net.URI;

@EnableConfigurationProperties(MovieDslProperties.class)
@Configuration
public class MovieDslConfiguration {

    @Bean
    public DataFlowOperations dataFlowOperations(){

        URI dataFlowUri = URI.create("http://localhost:9393");
        DataFlowOperations dataFlowOperations = new DataFlowTemplate(dataFlowUri);
        dataFlowOperations.appRegistryOperations().importFromResource(
                "https://dataflow.spring.io/rabbitmq-maven-latest", true);

        return dataFlowOperations;
    }

    @Bean
    public CommandLineRunner actions(MovieDslService movieDslService, MovieDslProperties movieDslProperties){
        return args -> {
            Method method = movieDslService.getClass()
                    .getMethod(movieDslProperties.getAction(),null);

            assert method != null;
            method.invoke(movieDslService,null);
        };
    }

    @Bean
    public MovieDslService movieDslService(DataFlowOperations dataFlowOperations){
        return new MovieDslService(dataFlowOperations);
    }

}
