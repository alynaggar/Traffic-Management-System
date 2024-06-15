package com.example.tms.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Camera {

    @Id
    private long id;

    private String name;

    private String url;

    @Transient
    private String locationName;

    @Enumerated(EnumType.STRING)
    private Status status = Status.UP;

    @ManyToOne
    @JsonIgnore
    private Location location;

    public Camera() {
    }

    public Camera(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Camera(long id, String name, String url, String locationName, Status status, Location location) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.locationName = locationName;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
