package com.example.tms.Service;

import com.example.tms.Entity.NotificationRequest;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FCMService {


    public void sendMessageToTopic(NotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToTopic(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private WebpushConfig getWebpushConfig(NotificationRequest request) {
        return WebpushConfig.builder()
                .setNotification(WebpushNotification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .setIcon("icon-url")
                        .build())
                .build();
    }

    private Message getPreconfiguredMessageToTopic(NotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .setTopic(request.getTopic())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationRequest request) {
        WebpushConfig webpushConfig = getWebpushConfig(request);
        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();
        return Message.builder()
                .setWebpushConfig(webpushConfig)
                .setNotification(notification);
    }

}