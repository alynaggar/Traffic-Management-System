package com.example.tms.WebSocket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;
    private static final Logger log = LoggerFactory.getLogger(SocketModule.class);

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", WebsocketMessage.class, onChatReceived());
        server.addEventListener("send_traffic", WebsocketMessage.class, onTrafficReceived());
        server.addEventListener("send_emergency", WebsocketMessage.class, onEmergencyReceived());
        server.addEventListener("send_accident", WebsocketMessage.class, onAccidentReceived());
    }

    private DataListener<WebsocketMessage> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Received chat message: {}", data);
            socketService.sendMessage(data.getRoom(), "get_message", senderClient, data.getMessage());
        };
    }

    private DataListener<WebsocketMessage> onTrafficReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Received traffic message: {}", data);
            socketService.sendTrafficMessage(data.getMessage());
        };
    }

    private DataListener<WebsocketMessage> onEmergencyReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Received emergency message: {}", data);
            socketService.sendEmergencyMessage(data.getMessage());
        };
    }

    private DataListener<WebsocketMessage> onAccidentReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Received accident message: {}", data);
            socketService.sendAccidentMessage(data.getMessage());
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}] Connected to socket", client.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }
}
