package com.apress.nats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.util.SerializationUtils;

import java.nio.charset.StandardCharsets;

@Log4j2
@AllArgsConstructor
@Data
public class NatsTemplate {

    private NatsConnection natsConnection;

    public void send(String subject, String message){
        assert this.natsConnection != null && subject != null && !subject.isEmpty() && message != null && !message.isEmpty();
        log.debug("Sending: {}", message);
        this.natsConnection.getConnection().publish(subject, message.getBytes(StandardCharsets.UTF_8));
    }

    public void send(String subject,Message<?> message){
        assert this.natsConnection != null && subject != null && !subject.isEmpty() && message != null;
        log.debug("Sending: {}", message);
        this.natsConnection.getConnection().publish(subject, SerializationUtils.serialize(message));
    }

}
