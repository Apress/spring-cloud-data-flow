package com.apress.cloud.stream.movie;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.router.AbstractMappingMessageRouter;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolver;


@Log4j2
@EnableBinding(MovieGenre.class)
public class MovieStream {

    //Acts as Processor
    @Bean
    @ServiceActivator(inputChannel = MovieGenre.INPUT)
    public AbstractMappingMessageRouter destinationRouter(@Qualifier("binderAwareChannelResolver") DestinationResolver<MessageChannel> channelResolver) {
        AbstractMappingMessageRouter router = new ExpressionEvaluatingRouter(new SpelExpressionParser().parseExpression("#jsonPath(payload,'$.genre')"));
        router.setDefaultOutputChannelName(MovieGenre.GENRE_DEFAULT);
        router.setChannelResolver(channelResolver);
        return router;
    }


    //Sinks
    @StreamListener(MovieGenre.GENRE_SCIENCE_FICTION)
    public void genreScienceFiction(Movie movie){
        log.info("Science Fiction: {}",movie);
    }


    @StreamListener(MovieGenre.GENRE_HORROR)
    public void genreHorror(Movie movie){
        log.info("Horror: {}",movie);
    }

    //...
}
