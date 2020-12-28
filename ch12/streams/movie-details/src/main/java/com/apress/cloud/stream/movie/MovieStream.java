package com.apress.cloud.stream.movie;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Log4j2
@EnableConfigurationProperties({MovieProperties.class, DropboxProperties.class})
@EnableBinding(Processor.class)
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

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Object process(Movie movie){
        try {
            getRequest.setURI(new URI(movieProperties.getApiServer().replace("ID", movie.getId())));
            HttpEntity entity = httpclient.execute(getRequest).getEntity();
            String url = JsonPath.parse(EntityUtils.toString(entity, StandardCharsets.UTF_8)).read("$.image.url",String.class).toString();
            log.debug("Movie's URL: {}", url);

            ObjectMapper mapper = new ObjectMapper();
            String payload = mapper.writeValueAsString(new MoviePayload(movieProperties.getTaskName(),
                    new String[] {
                            "url=" + url,
                            "imdbId=" + movie.getId(),
                            "dropbox.token=" + movieProperties.getDropbox().getToken(),
                            "dropbox.path=" + movieProperties.getDropbox().getPath(),
                            "dropbox.local-tmp-folder=" + movieProperties.getDropbox().getLocalTmpFolder()
                    }));

            return new GenericMessage<>(payload);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can't process the Movie.");

    }
}
