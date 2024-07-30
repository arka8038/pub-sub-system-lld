package com.pubsub.lld.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Topic {
    private final String topicId;
    private final String topicName;
    private final List<Message> messages;
    private final List<TopicSubscriber> topicSubscribers;

    public Topic(String topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.messages = new ArrayList<>();
        this.topicSubscribers = new ArrayList<>();
    }

    public void addMessage(@NonNull final Message message) {
        messages.add(message);
    }
    public void addSubscribers(@NonNull final TopicSubscriber subscriber) {
        topicSubscribers.add(subscriber);
    }
}
