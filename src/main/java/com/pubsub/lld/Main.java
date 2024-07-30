package com.pubsub.lld;

import com.pubsub.lld.model.Message;
import com.pubsub.lld.model.Topic;
import com.pubsub.lld.public_interface.Queue;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Queue queue = new Queue();

        Topic t1 = queue.createTopic("t1");
        Topic t2 = queue.createTopic("t2");

        final SleepingSubscriber sub1 = new SleepingSubscriber("sub1", 10000);
        final SleepingSubscriber sub2 = new SleepingSubscriber("sub2", 10000);

        queue.subscribe(t1, sub1);
        queue.subscribe(t1, sub2);

        final SleepingSubscriber sub3 = new SleepingSubscriber("sub3", 10000);
        queue.subscribe(t2, sub3);

        queue.publish(t1, new Message("m1"));
        queue.publish(t1, new Message("m2"));

        queue.publish(t2, new Message("m3"));

        Thread.sleep(15000);

        queue.publish(t2, new Message("m4"));
        queue.publish(t1, new Message("m5"));
    }
}