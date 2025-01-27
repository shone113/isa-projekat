package com.simplemq.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplemq.domain.Message;
import com.simplemq.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpMessageQueueClient implements MessageQueueClient {
    private final String baseUrl;
    private final ObjectMapper objectMapper;
    private MQService mqService;
    public HttpMessageQueueClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void sendMessage(String queueName, Object message) {
        try {
            URL url = new URL(baseUrl + "/produce?queue=" + queueName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setDoOutput(true);

            String jsonMessage = objectMapper.writeValueAsString(message);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonMessage.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to send message: HTTP " + responseCode);
            }
//            mqService.add(new Message(jsonMessage, queueName));

        } catch (Exception e) {
            throw new RuntimeException("Error sending message to queue: " + queueName, e);
        }
    }

    @Override
    public <T> T receiveMessage(String queueName, Class<T> messageType) {
        try {
            URL url = new URL(baseUrl + "/consume?queue=" + queueName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                return objectMapper.readValue(connection.getInputStream(), messageType);
            } else {
                throw new RuntimeException("Failed to receive message: HTTP " + connection.getResponseCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error receiving message from queue: " + queueName, e);
        }
    }
}


