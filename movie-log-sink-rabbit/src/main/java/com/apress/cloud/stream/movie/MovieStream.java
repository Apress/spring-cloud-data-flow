package com.apress.cloud.stream.movie;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;


@Log4j2
@EnableBinding(Sink.class)
public class MovieStream {


    @StreamListener(Sink.INPUT)
    public void process(Movie movie){
        log.info("Movie processed: {}",movie);
    }

    // Using Annotations
    /*
    @StreamListener(Sink.INPUT)
    public void process(@Payload String payload, @Header("contentType") String contentType,@Headers Map<String, Object> map){
        log.info("Payload processed: {} - ContentType: {} - Headers: {}",payload,contentType,map);
    }
    */

    /*
    @StreamListener(Sink.INPUT)
    public void process(Message message){
        log.info("Message processed: {} ",message);
    }
    */

    // Using @Payload and @Header
    // Using @StreamListeners conditions based on the Payload:
    /*
    @StreamListener(value = Sink.INPUT, condition = "#jsonPath(payload,'$.year') < 2000")
    public void processTwoThousandAndBelow(Movie movie){
        log.info("1990-2000 Movie processed: {}",movie);
    }

    @StreamListener(value = Sink.INPUT, condition = "#jsonPath(payload,'$.year') >= 2000")
    public void processTwoThousandAndAbove(Movie movie){
        log.info("2000-Present Movie processed: {}",movie);
    }
    */

    // Using @StreamListeners conditions based on the Header:
    /*
    @StreamListener(value = Sink.INPUT, condition = "headers['genre']=='science-fiction'")
    public void processScienceFiction(Movie movie){
        log.info("Science Fiction Movie processed: {}",movie);
    }

    @StreamListener(value = Sink.INPUT, condition = "headers['genre']=='drama'")
    public void processDrama(Movie movie){
        log.info("0 Stars Movie processed: {}",movie);
    }
    */

    // Another ways to consume Messages from the Source.INPUT
    /*
    @ServiceActivator(inputChannel = Sink.INPUT)
    public void movieProcess(Movie movie){
        log.info("Movie processed: {}",movie);
    }
    */
}

/*

// # Add the following property to the application.properties file:
// # ONLY: Binding when using Spring Cloud Function - Consumer
// spring.cloud.stream.bindings.log-in-0.destination=log

@Log4j2
@Configuration
public class MovieStream {

    @Bean
    public Consumer<Movie> log(){
        return movie -> {
            log.info("Movie Processed: {}", movie);
        };
    }
}
*/