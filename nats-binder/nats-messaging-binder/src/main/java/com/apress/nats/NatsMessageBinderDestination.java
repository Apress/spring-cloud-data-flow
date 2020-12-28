package com.apress.nats;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;

@AllArgsConstructor
@Data
public class NatsMessageBinderDestination implements ProducerDestination, ConsumerDestination {

    private final String destination;

    @Override
    public String getName() {
        return this.destination.trim();
    }

    @Override
    public String getNameForPartition(int partition) {
        throw new UnsupportedOperationException("Partition not yet implemented for Nats Binder");
    }
}
