package com.apress.cloud.stream.movie;

import lombok.AllArgsConstructor;
import org.springframework.cloud.dataflow.rest.client.DataFlowOperations;
import org.springframework.cloud.dataflow.rest.client.dsl.DeploymentPropertiesBuilder;
import org.springframework.cloud.dataflow.rest.client.dsl.Stream;

@AllArgsConstructor
public class MovieDslService {

    private DataFlowOperations dataFlowOperations;

    public void create(){
        java.util.stream.Stream.of(MovieDslStream.values()).forEach( c -> {
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


    private void createStream(String name, String definition){
        Stream.builder(dataFlowOperations)
                .name(name)
                .definition(definition)
                .create();
    }

    private void deployStream(String name){
        dataFlowOperations.streamOperations().deploy(name,
                new DeploymentPropertiesBuilder().build());
    }

    private void destroyStream(String name){
        dataFlowOperations.streamOperations().destroy(name);
    }
}
