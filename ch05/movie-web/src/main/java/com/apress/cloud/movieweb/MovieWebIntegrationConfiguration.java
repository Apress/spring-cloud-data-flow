package com.apress.cloud.movieweb;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.messaging.MessageHandler;

import javax.sql.DataSource;

@AllArgsConstructor
@EnableConfigurationProperties(MovieWebProperties.class)
@Configuration
public class MovieWebIntegrationConfiguration {

    private MovieWebProperties movieWebProperties;
    private IntegrationFlowContext integrationFlowContext;


    @Bean
    public IntegrationFlow httpFlow() {
        return IntegrationFlows.from(Http
                    .inboundChannelAdapter(movieWebProperties.getPath())
                    .requestPayloadType(Movie.class)
                    .requestMapping(m -> m.methods(HttpMethod.POST)))
                .channel(MessageChannels.publishSubscribe("publisher"))
                .get();
    }

    @Bean
    public IntegrationFlow logFlow() {
        return IntegrationFlows.from("publisher")
                .log(LoggingHandler.Level.INFO,"Movie", m -> m)
                .get();
    }

    @Bean
    @ServiceActivator(inputChannel = "publisher")
    public MessageHandler process(DataSource dataSource){
        JdbcMessageHandler jdbcMessageHandler = new JdbcMessageHandler(dataSource,
                "INSERT INTO movies (title,actor,year) VALUES (?, ?, ?)");
        jdbcMessageHandler.setPreparedStatementSetter((ps, message) -> {
            ps.setString(1,((Movie)message.getPayload()).getTitle());
            ps.setString(2,((Movie)message.getPayload()).getActor());
            ps.setInt(3,((Movie)message.getPayload()).getYear());
        });
        jdbcMessageHandler.setOrder(1);
        return jdbcMessageHandler;
    }
}
