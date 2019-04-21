package fi.tuni.mycalendarapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Event implements Parcelable {

    private long id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private EventType eventType;

    public Event() {
        eventType = new EventType();
    }

    public Event(String name, String description, LocalDate date, LocalTime time, EventType eventType) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.eventType = eventType;
    }

    public Event(Parcel in) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        String[] data = new String[7];
        in.readStringArray(data);
        this.id = Long.parseLong(data[0]);
        this.name = data[1];
        this.description = data[2];

        try {
            this.date = LocalDate.parse(data[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.time = LocalTime.parse(data[4]);
        this.eventType = new EventType(data[5], data[6]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{"" + this.id, this.name, this.description, this.date.toString(), this.time.toString(), this.eventType.getName(), this.eventType.getColorCode()});
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String toString() {
        return time.toString() + " " + name + ": " + description;
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }

    };

}
