package com.pubsub.lld.public_interface;

import com.pubsub.lld.handlers.TopicHandler;
import com.pubsub.lld.model.Message;
import com.pubsub.lld.model.Topic;
import com.pubsub.lld.model.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Queue {
    // Map to store topic handlers with topic ID as the key
    private final Map<String, TopicHandler> topicHandlers;

    // ExecutorService to manage threads for publishing messages
    private final ExecutorService executorService;

    // Constructor to initialize the topic handlers map and executor service
    public Queue() {
        this.topicHandlers = new HashMap<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    // Method to create a new topic
    public Topic createTopic(String topicName) {
        // Create a new Topic with a unique ID
        Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
        // Create a handler for the new topic
        TopicHandler topicHandler = new TopicHandler(topic);
        // Add the topic handler to the map
        topicHandlers.put(topic.getTopicId(), topicHandler);
        System.out.println("Created topic: " + topic.getTopicName());
        return topic;
    }

    // Method to publish a message to a topic
    public void publish(Topic topic, Message message) {
        // Add the message to the topic
        topic.addMessage(message);
        System.out.println(message.getMessage() + " published to topic: " + topic.getTopicName());
        // Submit the publish task to the executor service for asynchronous execution
        executorService.submit(() -> topicHandlers.get(topic.getTopicId()).publish());
    }

    // Method to subscribe to a topic
    public void subscribe(Topic topic, ISubscriber subscriber) {
        // Add a new subscriber to the topic
        topic.addSubscribers(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getId() + " subscribed to topic: " + topic.getTopicName());
    }
}
