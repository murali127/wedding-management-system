package com.wedding.servlets;

public class Event {
    private int id;
    private String name;
    private String date;
    private String time;
    private String venue;
    private boolean rsvped; // Add this field

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    // Add getter and setter for rsvped
    public boolean isRsvped() {
        return rsvped;
    }

    public void setRsvped(boolean rsvped) {
        this.rsvped = rsvped;
    }
}