package com.apress.nats;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.nats")
public class NatsProperties {

    private String host = "localhost";
    private Integer port = 4222;
}
