package com.wedding.servlets;

public class RSVPDetail {
    private int rsvpId;
    private String username;
    private String eventName;

    // Getters and Setters
    public int getRsvpId() {
        return rsvpId;
    }

    public void setRsvpId(int rsvpId) {
        this.rsvpId = rsvpId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}