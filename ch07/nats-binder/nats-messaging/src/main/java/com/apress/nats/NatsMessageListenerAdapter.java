package com.apress.nats;

import io.nats.client.Dispatcher;
import io.nats.client.Subscription;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class NatsMessageListenerAdapter {

    private NatsConnection natsConnection;
    private String subject;
    private NatsMessageListener adapter;
    private Subscription subscription;
    private Dispatcher dispatcher;

    public void start(){
        assert natsConnection != null && natsConnection.getConnection() != null && subject != null && adapter != null;
        log.debug("Creating Message Listener...");
        dispatcher = this.natsConnection.getConnection().createDispatcher((msg) -> {});
        subscription = dispatcher.subscribe(this.subject, (msg) -> {
            adapter.onMessage(msg.getData());
        });
        log.debug("Subscribed to: {}",this.subject);
    }

    public void stop(){
        assert dispatcher != null && subject != null;
        log.debug("Unsubscribing from: {}", subject);
        dispatcher.unsubscribe(subject,300);
    }
}
