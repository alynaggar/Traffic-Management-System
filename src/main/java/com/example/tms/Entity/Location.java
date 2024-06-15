package com.example.tms.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status = Status.UP;

    private double longitude;

    private double latitude;

    @OneToMany(mappedBy = "location")
    private List<Camera> cameras;

    public Location() {
    }

    public Location(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Location(long id, String name, Status status, double longitude, double latitude, List<Camera> cameras) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cameras = cameras;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }
}
