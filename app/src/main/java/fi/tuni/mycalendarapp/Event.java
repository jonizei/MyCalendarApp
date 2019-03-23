package fi.tuni.mycalendarapp;

import java.util.Date;

public class Event {

    private static long numberOfEvents = 0;

    private long id;
    private String name;
    private String description;
    private Date date;
    private Time time;

    public Event() {

    }

    public Event(String name, String description, Date date, Time time) {
        this.id = numberOfEvents++;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
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

    public String toString() {
        return time.toString() + " " + name;
    }

}
