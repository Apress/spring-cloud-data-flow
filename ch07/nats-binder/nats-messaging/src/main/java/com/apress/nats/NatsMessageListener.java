package com.apress.nats;

public interface NatsMessageListener  {
    void onMessage(byte[] message);
}
