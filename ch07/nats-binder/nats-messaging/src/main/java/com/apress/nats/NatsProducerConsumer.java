package com.apress.nats;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Log4j2
@Configuration
public class NatsProducerConsumer {

	// This class is just for TESTING Purposes.
    /*
    @Bean(initMethod = "start",destroyMethod = "stop")
    public NatsMessageListenerAdapter natsMessageListenerAdapter(NatsConnection natsConnection){
        NatsMessageListenerAdapter adapter = new NatsMessageListenerAdapter();
        adapter.setNatsConnection(natsConnection);
        adapter.setSubject("test");
        adapter.setAdapter( message -> {
            log.info("Received: {}", new String(message, StandardCharsets.UTF_8));
        });
        return adapter;
    }
    @Bean
    public ApplicationRunner sendMessage(NatsTemplate natsTemplate){
        return args -> {
            natsTemplate.send("test","Hello There!");
        };
    }
    */
}
