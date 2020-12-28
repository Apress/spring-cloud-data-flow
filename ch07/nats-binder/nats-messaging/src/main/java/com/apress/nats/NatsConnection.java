package com.apress.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;
import lombok.Data;

import java.io.IOException;

@Data
public class NatsConnection {
    private Connection connection;
    private NatsProperties natsProperties;
    private NatsConnection(){}

    public NatsConnection(NatsProperties natsProperties) throws IOException, InterruptedException {
        this.natsProperties = natsProperties;
        this.connection = Nats.connect("nats://" + natsProperties.getHost() + ":" + natsProperties.getPort().toString());
    }

}
