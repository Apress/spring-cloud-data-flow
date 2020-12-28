package com.apress.nats;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@Log4j2
@EnableBinding({Source.class, Sink.class})
public class NatsStream {

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedRate = "10000", maxMessagesPerPoll = "1"))
    public MessageSource<String> timerMessageSource() {
        return () -> new GenericMessage<>("Hello Spring Cloud Stream");
    }

    @StreamListener(Sink.INPUT)
    public void process(Object message){
        log.info("Received and ProcessedClass: {}", message.getClass().getName());
        log.info("Received and Processed: {}", message);
    }
}
