package com.simplemq.client;

public interface MessageQueueClient {
    void sendMessage(String queueName, Object message);
    <T> T receiveMessage(String queueName, Class<T> messageType);
}
