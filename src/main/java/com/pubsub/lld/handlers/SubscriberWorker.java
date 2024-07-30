package com.pubsub.lld.handlers;

import com.pubsub.lld.model.Message;
import com.pubsub.lld.model.Topic;
import com.pubsub.lld.model.TopicSubscriber;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final TopicSubscriber subscriber;

    @Override
    public void run() {
        // Continuously process messages until the thread is interrupted
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (subscriber) {
                // Process messages for the subscriber
                processMessages();
            }
        }
    }

    // Method to process messages for the subscriber
    private void processMessages() {
        try {
            int currOffset = subscriber.getOffset().get();
            // Wait until there are new messages available for the subscriber
            while (currOffset >= topic.getMessages().size()) {
                subscriber.wait();
            }
            // Consume the message if available
            consumeMessage(currOffset);
        } catch (InterruptedException e) {
            // Preserve interrupt status and exit the loop
            Thread.currentThread().interrupt();
        }
    }

    // Method to consume a message and update the subscriber's offset
    private void consumeMessage(int currOffset) throws InterruptedException {
        // Get the message at the current offset
        Message message = topic.getMessages().get(currOffset);
        // Pass the message to the subscriber's consume method
        subscriber.getSubscriber().consume(message);
        // Update the offset atomically
        subscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
    }

    // Method to wake up the subscriber if needed
    public void wakeUpIfNeeded() {
        synchronized (subscriber) {
            subscriber.notify();
        }
    }
}
