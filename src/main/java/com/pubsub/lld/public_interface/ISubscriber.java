package com.pubsub.lld.public_interface;

import com.pubsub.lld.model.Message;

public interface ISubscriber {
    String getId();

    void consume(Message message) throws InterruptedException;
}
