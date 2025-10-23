package com.example.booking.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private Long id;
    private String userName;
    private String resource;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking(Long id, String userName, String resource,
                   LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.userName = userName;
        this.resource = resource;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Booking(String userName, String resource,
                   LocalDateTime startTime, LocalDateTime endTime) {
        this(null, userName, resource, startTime, endTime);
    }

    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getResource() { return resource; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    public void setId(Long id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setResource(String resource) { this.resource = resource; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override public String toString() {
        return "Booking{id=%s, user=%s, res=%s, %s -> %s}"
               .formatted(id, userName, resource, startTime, endTime);
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking b)) return false;
        return Objects.equals(id, b.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
