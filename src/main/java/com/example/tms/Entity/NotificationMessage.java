package com.example.tms.Entity;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationMessage {

    private Location location;
    private Camera camera;
    private ArrayList<String> statues;
    private int[] greenTime;
    private int[] redTime;

    // Constructor for sending location-based messages
    public NotificationMessage(Location location) {
        this.location = location;
        this.camera = null;
        this.statues = null;
        this.greenTime = null;
        this.redTime = null;
    }

    // Constructor for sending camera-based messages
    public NotificationMessage(Camera camera) {
        this.location = null;
        this.camera = camera;
        this.statues = null;
        this.greenTime = null;
        this.redTime = null;
    }

    // Constructor for sending traffic messages
    public NotificationMessage(Location location, ArrayList<String> statues, int[] greenTime, int[] redTime) {
        this.location = location;
        this.camera = null;
        this.statues = statues;
        this.greenTime = greenTime;
        this.redTime = redTime;
    }

    // Getter and setter methods
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public int[] getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int[] greenTime) {
        this.greenTime = greenTime;
    }

    public int[] getRedTime() {
        return redTime;
    }

    public void setRedTime(int[] redTime) {
        this.redTime = redTime;
    }

    public ArrayList<String> getStatues() {
        return statues;
    }

    public void setStatues(ArrayList<String> statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "location=" + (location != null ? location.toString() : "null") +
                ", camera=" + (camera != null ? camera.toString() : "null") +
                ", statues=" + (statues != null ? statues.toString() : "null") +
                ", greenTime=" + (greenTime != null ? Arrays.toString(greenTime) : "null") +
                ", redTime=" + (redTime != null ? Arrays.toString(redTime) : "null") +
                '}';
    }
}
