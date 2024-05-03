package com.example.tms.WebSocket;

public class WebsocketMessage {
    private MessageType type;
    private String message;
    private String room;

    public WebsocketMessage() {
    }

    public WebsocketMessage(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
