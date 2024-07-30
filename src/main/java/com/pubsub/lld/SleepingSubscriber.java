package com.pubsub.lld;

import com.pubsub.lld.model.Message;
import com.pubsub.lld.public_interface.ISubscriber;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SleepingSubscriber implements ISubscriber {
    private final String id;
    private final int sleepTimeInMillis;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void consume(Message message) throws InterruptedException {
        System.out.println("Subscriber: " + id + " started consuming: " + message.getMessage());
        Thread.sleep(sleepTimeInMillis);
        System.out.println("Subscriber: " + id + " done consuming: " + message.getMessage());
    }
}
