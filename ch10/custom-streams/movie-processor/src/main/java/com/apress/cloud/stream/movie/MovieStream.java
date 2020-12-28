package com.apress.cloud.stream.movie;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Log4j2
@EnableConfigurationProperties(MovieProperties.class)
@Configuration
public class MovieStream {

    private MovieProperties movieProperties;
    private final CloseableHttpClient httpclient = HttpClients.createDefault();
    private final HttpGet getRequest = new HttpGet();

    public MovieStream(MovieProperties movieProperties) {
        this.movieProperties = movieProperties;
        getRequest.addHeader("Accept", "application/json");
        getRequest.addHeader("x-rapidapi-host", movieProperties.getHeaderHost());
        getRequest.addHeader("x-rapidapi-key", movieProperties.getHeaderKey());
        getRequest.addHeader("Content-Type", "application/json");
    }

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> movieProcessor(ObjectMapper objectMapper) {
        return movieFlux -> movieFlux.map(
                movie -> {
                    try {

                        getRequest.setURI(new URI(movieProperties.getApiServer().replace("ID", movie.getId())));
                        HttpEntity entity = httpclient.execute(getRequest).getEntity();
                        movie.setImdb(objectMapper.readValue(EntityUtils.toString(entity, StandardCharsets.UTF_8), MovieImdb.class));

                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }

                    log.debug("About ot send: {}", movie);
                    return movie;
                });
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    }

}
