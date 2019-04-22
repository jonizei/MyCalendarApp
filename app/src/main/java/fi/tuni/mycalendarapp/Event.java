package fi.tuni.mycalendarapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * This class represents a event that is used in the calendar
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class Event implements Parcelable {

    /**
     * Event's id
     */
    private long id;

    /**
     * Event's name
     */
    private String name;

    /**
     * Event's description
     */
    private String description;

    /**
     * Event's date
     */
    private LocalDate date;

    /**
     * Event's time
     */
    private LocalTime time;

    /**
     * Event's label that shows the events urgency
     */
    private EventType eventType;

    /**
     * Id that identifies notifications
     */
    private int notificationId;

    /**
     * Constructor for Event which initializes eventType to default
     */
    public Event() {
        eventType = new EventType();
    }

    /**
     * Constructor for Event
     *
     * @param name Event's name
     * @param description Event's description
     * @param date Event's date
     * @param time Event's time
     * @param eventType Event's type
     */
    public Event(String name, String description, LocalDate date, LocalTime time, EventType eventType) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.eventType = eventType;
    }

    /**
     * Event Parcel constructor which is required if object is passed inside Intent extras
     *
     * @param in Parcel object
     */
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

    /**
     * Writes the object to the parcel
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{"" + this.id, this.name, this.description, this.date.toString(), this.time.toString(), this.eventType.getName(), this.eventType.getColorCode()});
    }

    /**
     * Sets value for id
     *
     * @param id Event's id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Sets value for name
     *
     * @param name Event's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets value for description
     *
     * @param description Event's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets value for date
     *
     * @param date Event's date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets value for time
     *
     * @param time Event's time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Sets value for eventType
     *
     * @param eventType Event's type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Returns id
     *
     * @return Event's id
     */
    public long getId() {
        return id;
    }

    /**
     * Returns name
     *
     * @return Event's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns description
     *
     * @return Event's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns date
     *
     * @return Event's date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns time
     *
     * @return Event's time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Returns eventType
     *
     * @return Event's type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Shows event values in a string
     *
     * @return String which contains time, name and description
     */
    public String toString() {
        return time.toString() + " " + name + ": " + description;
    }

    /**
     * This class is used for creating parcerable object
     */
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        /**
         * Creates Event object
         *
         * @param source Parcel object
         * @return Event object
         */
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        /**
         * Creates array of events
         *
         * @param size size of the array
         * @return Event array
         */
        public Event[] newArray(int size) {
            return new Event[size];
        }

    };

    /**
     * Sets value for notificationId
     *
     * @param notificationId Event's notification id
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Returns notificationId
     *
     * @return Events notification id
     */
    public int getNotificationId() {
        return notificationId;
    }

}
