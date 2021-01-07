package com.apress.cloud.stream.movie;

import lombok.AllArgsConstructor;
import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
import org.springframework.cloud.dataflow.rest.client.dsl.DeploymentPropertiesBuilder;
import org.springframework.cloud.dataflow.rest.client.dsl.Stream;
import org.springframework.cloud.dataflow.rest.client.dsl.StreamApplication;
import org.springframework.cloud.dataflow.rest.client.dsl.StreamBuilder;

@AllArgsConstructor
public class MovieDslService {

    private DataFlowOperations dataFlowOperations;
    private StreamApplication httpSource;
    private StreamApplication splitterProcessor;
    private StreamApplication groovyTransformProcessor;
    private StreamApplication jdbcSink;

    public void create(){
        createFluentStream(MovieDslStream.MOVIE.getName());
        java.util.stream.Stream.of(MovieDslStream.values()).filter(c -> !c.getName().equals(MovieDslStream.MOVIE.getName())).forEach( c -> {
            createStream(c.getName(),c.getDefinition());
        });
    }

    public void deploy(){
        java.util.stream.Stream.of(MovieDslStream.values()).forEach( c -> {
            deployStream(c.getName());
        });
    }

    public void destroy(){
        java.util.stream.Stream.of(MovieDslStream.values()).forEach( c -> {
            destroyStream(c.getName());
        });
    }


    private void createFluentStream(String name){
        Stream.builder(dataFlowOperations)
                .name(name)
                .source(httpSource)
                .processor(splitterProcessor)
                .processor(groovyTransformProcessor)
                .sink(jdbcSink)
                .create();
    }

    private void createStream(String name, String definition){
        Stream.builder(dataFlowOperations)
                .name(name)
                .definition(definition)
                .create();
    }

    private void deployStream(String name){
        dataFlowOperations.streamOperations().deploy(name,new DeploymentPropertiesBuilder().build());
    }

    private void destroyStream(String name){
        dataFlowOperations.streamOperations().destroy(name);
    }
}
