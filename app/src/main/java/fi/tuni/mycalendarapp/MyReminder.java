package fi.tuni.mycalendarapp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class MyReminder {

    private static final String TAG = "MyReminder";

    private Event event;
    private int hour;
    private int minute;
    private int dayOfTheYear;

    public MyReminder() {

    }

    public MyReminder(Event event, int hour, int minute, int dayOfTheYear) {
        this.event = event;
        this.hour = hour;
        this.minute = minute;
        this.dayOfTheYear = dayOfTheYear;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDayOfTheYear() {
        return dayOfTheYear;
    }

    public void setDayOfTheYear(int dayOfTheYear) {
        this.dayOfTheYear = dayOfTheYear;
    }

}
