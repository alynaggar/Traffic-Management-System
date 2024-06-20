package com.example.tms.WebSocket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    private final SocketIOServer server;

    public SocketService(SocketIOServer server) {
        this.server = server;
    }

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, Object object) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            client.sendEvent(eventName, new WebsocketMessage(MessageType.SERVER, object));
        }
    }

    public void sendTrafficMessage(Object object) {
        for (SocketIOClient client : server.getRoomOperations("Traffic").getClients()) {
            client.sendEvent("traffic_alert", new WebsocketMessage(MessageType.SERVER, object));
        }
    }

    public void sendEmergencyMessage(Object object) {
        for (SocketIOClient client : server.getRoomOperations("Emergency").getClients()) {
            client.sendEvent("emergency_alert", new WebsocketMessage(MessageType.SERVER, object));
        }
    }

    public void sendAccidentMessage(Object object) {
        for (SocketIOClient client : server.getRoomOperations("Accident").getClients()) {
            client.sendEvent("accident_alert", new WebsocketMessage(MessageType.SERVER, object));
        }
    }
}
