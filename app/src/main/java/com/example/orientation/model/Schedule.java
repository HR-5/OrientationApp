package com.example.orientation.model;

import java.util.ArrayList;

public class Schedule {
    public String date;
    public ArrayList<Event> events;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
