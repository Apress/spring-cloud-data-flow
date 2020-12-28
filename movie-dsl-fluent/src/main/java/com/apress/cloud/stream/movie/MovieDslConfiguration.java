package com.apress.cloud.stream.movie;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.dataflow.core.ApplicationType;
import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;
import org.springframework.cloud.dataflow.rest.client.dsl.StreamApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.net.URI;

@EnableConfigurationProperties(MovieDslProperties.class)
@Configuration
public class MovieDslConfiguration {

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
    public MovieDslService movieDslService(DataFlowOperations dataFlowOperations,
                                           StreamApplication httpSource,StreamApplication splitterProcessor,StreamApplication groovyTransformProcessor,
                                           StreamApplication jdbcSink, StreamApplication logSink){
        return new MovieDslService(dataFlowOperations,httpSource,splitterProcessor,groovyTransformProcessor,jdbcSink);
    }

    @Bean
    public DataFlowOperations dataFlowOperations(){

        URI dataFlowUri = URI.create("http://localhost:9393");
        DataFlowOperations dataFlowOperations = new DataFlowTemplate(dataFlowUri);
  

        dataFlowOperations.appRegistryOperations().register("http", ApplicationType.source,
                "maven://org.springframework.cloud.stream.app:http-source-rabbit:2.1.4.RELEASE",
                "maven://org.springframework.cloud.stream.app:http-source-rabbit:jar:metadata:2.1.4.RELEASE",
                true);
        dataFlowOperations.appRegistryOperations().register("splitter", ApplicationType.processor,
                "maven://org.springframework.cloud.stream.app:splitter-processor-rabbit:2.1.3.RELEASE",
                "maven://org.springframework.cloud.stream.app:splitter-processor-rabbit:jar:metadata:2.1.3.RELEASE",
                true);
        dataFlowOperations.appRegistryOperations().register("groovy-transform", ApplicationType.processor,
                "maven://org.springframework.cloud.stream.app:groovy-transform-processor-rabbit:2.1.3.RELEASE",
                "maven://org.springframework.cloud.stream.app:groovy-transform-processor-rabbit:jar:metadata:2.1.3.RELEASE",
                true);
        dataFlowOperations.appRegistryOperations().register("filter", ApplicationType.processor,
                "maven://org.springframework.cloud.stream.app:filter-processor-rabbit:2.1.3.RELEASE",
                "maven://org.springframework.cloud.stream.app:filter-processor-rabbit:jar:metadata:2.1.3.RELEASE",
                true);
        dataFlowOperations.appRegistryOperations().register("jdbc", ApplicationType.sink,
                "maven://org.springframework.cloud.stream.app:jdbc-sink-rabbit:2.1.6.RELEASE",
                "maven://org.springframework.cloud.stream.app:jdbc-sink-rabbit:jar:metadata:2.1.6.RELEASE",
                true);
        dataFlowOperations.appRegistryOperations().register("log", ApplicationType.sink,
                "maven://org.springframework.cloud.stream.app:log-sink-rabbit:2.1.4.RELEASE",
                "maven://org.springframework.cloud.stream.app:log-sink-rabbit:jar:metadata:2.1.4.RELEASE",
                true);

        return dataFlowOperations;
    }

    @Bean
    public StreamApplication httpSource(){
        return new StreamApplication("http")
                .addProperty("port",9001);
    }

    @Bean
    public StreamApplication splitterProcessor(){
        return new StreamApplication("splitter")
                .addProperty("expression","\"#jsonPath(payload,'$.movies')\"");
    }

    @Bean
    public StreamApplication groovyTransformProcessor(){
        return new StreamApplication("groovy-transform")
                .addProperty("script","\"https://raw.githubusercontent.com/felipeg48/scdf-scripts/master/movie-transform.groovy\"");
    }

    @Bean
    public StreamApplication jdbcSink(){
        return new StreamApplication("jdbc")
                .addProperty("columns","\"id:id,title:title,actor:actor,year:year,genre:genre,stars:stars,rating:imdb.rating,ratingcount:imdb.ratingCount\"")
                .addProperty("table-name","\"movies\"")
                .addProperty("username","\"root\"")
                .addProperty("password","\"rootpw\"")
                .addProperty("driver-class-name","\"org.mariadb.jdbc.Driver\"")
                .addProperty("url","\"jdbc:mysql://mysql:3306/reviews?autoReconnect=true&useSSL=false\"");
    }
}
