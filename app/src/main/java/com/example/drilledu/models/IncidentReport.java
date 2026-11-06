package com.example.drilledu.models;

import java.io.Serializable;

public class IncidentReport implements Serializable {
    private long id;
    private String title;
    private String category; // Electrical, Fire Hazard, Exit Blocked, Structural, Water Leakage
    private String location; // Room 102, Science Lab, Auditorium, Ground Floor
    private String urgency; // LOW, MEDIUM, HIGH, CRITICAL
    private String description;
    private long timestamp;
    private String status; // LOGGED, UNDER REVIEW, RESOLVED

    public IncidentReport(long id, String title, String category, String location,
                          String urgency, String description, long timestamp, String status) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.location = location;
        this.urgency = urgency;
        this.description = description;
        this.timestamp = timestamp;
        this.status = status;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getUrgency() { return urgency; }
    public String getDescription() { return description; }
    public long getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}