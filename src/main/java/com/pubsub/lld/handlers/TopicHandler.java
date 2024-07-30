package com.pubsub.lld.handlers;

import com.pubsub.lld.model.Topic;
import com.pubsub.lld.model.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {
    private final Topic topic;
    private final Map<String, SubscriberWorker> subscriberWorkers;

    // Constructor to initialize the topic and subscriber workers map
    public TopicHandler(Topic topic) {
        this.topic = topic;
        this.subscriberWorkers = new HashMap<>();
    }

    // Method to publish messages to all subscribers of the topic
    public void publish() {
        // Iterate over all topic subscribers
        for (TopicSubscriber subscriber : topic.getTopicSubscribers()) {
            // Start or wake up subscriber workers
            startSubscriberWorkers(subscriber);
        }
    }

    // Method to start or wake up subscriber workers
    private void startSubscriberWorkers(TopicSubscriber subscriber) {
        String subId = subscriber.getSubscriber().getId();

        // Check if the worker for this subscriber already exists
        if (!subscriberWorkers.containsKey(subId)) {
            // Create a new worker and start a new thread for it
            SubscriberWorker subscriberWorker = new SubscriberWorker(topic, subscriber);
            subscriberWorkers.put(subId, subscriberWorker);
            new Thread(subscriberWorker).start();
        } else {
            // Wake up the existing worker if needed
            SubscriberWorker subscriberWorker = subscriberWorkers.get(subId);
            subscriberWorker.wakeUpIfNeeded();
        }
    }
}
