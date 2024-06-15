package com.example.tms.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrafficMessage {

    private Location location;

    private ArrayList<String> statues;

    private int[] greenTime;

    public TrafficMessage(Location location, ArrayList<String> statues, int[] greenTime) {
        this.location = location;
        this.statues = statues;
        this.greenTime = greenTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public int[] getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int[] greenTime) {
        this.greenTime = greenTime;
    }

    public ArrayList<String> getStatues() {
        return statues;
    }

    public void setStatues(ArrayList<String> statues) {
        this.statues = statues;
    }

    @Override
    public String toString() {
        return "TrafficMessage{" +
                "location=" + location +
                ", greenTime=" + Arrays.toString(greenTime) +
                '}';
    }
}
