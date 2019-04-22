package fi.tuni.mycalendarapp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class contains required data to create reminder
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class MyReminder {

    /**
     * Classe's tag for debugging
     */
    private static final String TAG = "MyReminder";

    /**
     * Event object
     */
    private Event event;

    /**
     * Reminder hour
     */
    private int hour;

    /**
     * Reminder minute
     */
    private int minute;

    /**
     * Reminder day of the year
     */
    private int dayOfTheYear;

    /**
     * MyReminder default constructor
     */
    public MyReminder() {

    }

    /**
     * Constructor for MyReminder
     *
     * @param event Event object
     * @param hour Reminder hour
     * @param minute Reminder minute
     * @param dayOfTheYear Reminder day of the year
     */
    public MyReminder(Event event, int hour, int minute, int dayOfTheYear) {
        this.event = event;
        this.hour = hour;
        this.minute = minute;
        this.dayOfTheYear = dayOfTheYear;
    }

    /**
     * Sets value for event
     *
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Returns event
     *
     * @return Event object
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Returns hour
     *
     * @return Reminder hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Sets value for hour
     *
     * @param hour Reminder hour
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Returns minute
     *
     * @return Reminder minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets value for minute
     *
     * @param minute Reminder minute
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * Returns day of the year
     *
     * @return Reminder day of the year
     */
    public int getDayOfTheYear() {
        return dayOfTheYear;
    }

    /**
     * Sets value for dayOfTheYear
     *
     * @param dayOfTheYear Reminder day of the year
     */
    public void setDayOfTheYear(int dayOfTheYear) {
        this.dayOfTheYear = dayOfTheYear;
    }

}
