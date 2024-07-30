package com.pubsub.lld.model;

import com.pubsub.lld.public_interface.ISubscriber;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TopicSubscriber {
    private final AtomicInteger offset;
    private final ISubscriber subscriber;

    public TopicSubscriber(ISubscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }
}
