package com.apress.nats;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@EnableConfigurationProperties(NatsProperties.class)
@Configuration
public class NatsConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NatsConnection natsConnection(NatsProperties natsProperties) throws IOException, InterruptedException {
        return new NatsConnection(natsProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public NatsTemplate natsTemplate(NatsConnection natsConnection){
        return new NatsTemplate(natsConnection);
    }

}
