package com.example.tms.Entity;

import jakarta.persistence.*;

@Entity
public class Camera {

    @Id
    private long id;

    private String name;

    private String url;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Location location;

    public Camera() {
    }

    public Camera(long id, String name, String url, Status status, Location location) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.status = status;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
