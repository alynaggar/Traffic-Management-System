package com.example.tms.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TrafficLight {

    @Id
    private long id;

    private String status;

    @ManyToOne
    private Location location;

    public TrafficLight() {
    }

    public TrafficLight(long id, String status, Location location) {
        this.id = id;
        this.status = status;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
