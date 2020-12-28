package com.apress.nats;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.SerializationUtils;

import java.nio.charset.StandardCharsets;

@Log4j2
public class NatsMessageBinderProducer extends MessageProducerSupport {

    private ConsumerDestination destination;
    private NatsMessageListenerAdapter adapter = new NatsMessageListenerAdapter();

    public NatsMessageBinderProducer(ConsumerDestination destination, NatsConnection natsConnection){
        assert destination != null && natsConnection != null;
        adapter.setSubject(destination.getName());
        adapter.setNatsConnection(natsConnection);
        adapter.setAdapter(messageListener);
    }

    @Override
    protected void doStart() {
        adapter.start();
    }

    @Override
    protected void doStop() {
        adapter.stop();
        super.doStop();
    }

    private NatsMessageListener messageListener = message -> {
        log.debug("[BINDER] Message received from NATS: {}",message);
        log.debug("[BINDER] Message Type received from NATS: {}",message.getClass().getName());
        this.sendMessage((Message<?>)SerializationUtils.deserialize(message));
    };
}
