package com.apress.cloud.stream.movie;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"/META-INF/spring/movie-log.xml"})
@EnableBinding(Sink.class)
public class MovieStream {
}
