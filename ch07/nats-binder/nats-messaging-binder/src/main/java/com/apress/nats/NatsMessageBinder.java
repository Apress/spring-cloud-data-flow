package com.apress.nats;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Log4j2
public class NatsMessageBinder extends AbstractMessageChannelBinder<ConsumerProperties, ProducerProperties,NatsMessageBinderProvisioningProvider> {

    private NatsTemplate natsTemplate;

    public NatsMessageBinder(String[] headersToEmbed, NatsMessageBinderProvisioningProvider provisioningProvider, NatsTemplate natsTemplate) {
        super(headersToEmbed, provisioningProvider);
        this.natsTemplate = natsTemplate;
    }

    @Override
    protected MessageHandler createProducerMessageHandler(ProducerDestination destination, ProducerProperties producerProperties, MessageChannel errorChannel) throws Exception {
        return message -> {
            assert natsTemplate != null;
            log.debug("[BINDER] Sending to NATS: {}",message);
            natsTemplate.send(destination.getName(),message);
        };
    }

    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ConsumerProperties properties) throws Exception {
        assert natsTemplate != null;
        return new NatsMessageBinderProducer(destination, this.natsTemplate.getNatsConnection());
    }
}
