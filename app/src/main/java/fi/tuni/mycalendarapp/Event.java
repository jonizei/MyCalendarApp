package fi.tuni.mycalendarapp;

import java.io.Serializable;
import java.util.Date;

public class Event {

    private long id;
    private String name;
    private String description;
    private Date date;
    private Time time;
    private EventType eventType;

    public Event() {
        eventType = new EventType();
    }

    public Event(String name, String description, Date date, Time time, EventType eventType) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.eventType = eventType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String toString() {
        return time.toString() + " " + name + ": " + description;
    }

}
