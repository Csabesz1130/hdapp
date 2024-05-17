package com.yourcompany.hdapp.models;

public class Location {
    private String id;
    private String name;
    private String status;

    public Location() {
        // Default constructor for Firestore
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
