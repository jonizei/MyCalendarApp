package fi.tuni.mycalendarapp;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    public enum TimeType {
        NONE,
        SECOND,
        MINUTE,
        HOUR,
        DAY,
        WEEK
    }

    private static long SECOND_MILLIS = 1000;
    private static long MINUTE_MILLIS = SECOND_MILLIS * 60;
    private static long HOUR_MILLIS = MINUTE_MILLIS * 60;
    private static long DAY_MILLIS = HOUR_MILLIS * 24;
    private static long WEEK_MILLIS = DAY_MILLIS * 7;

    public static boolean compareDates(Date d1, Date d2) {

        Date a = getZeroTimeDate(d1);
        Date b = getZeroTimeDate(d2);

        if(a.compareTo(b) == 0) {
            return true;
        }

        return false;
    }

    private static Date getZeroTimeDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();

        return date;
    }

    public static long getMillisecods(TimeType type) {

        long millis = 0;

        switch(type) {
            case NONE:
                millis = 0;
                break;
            case SECOND:
                millis = SECOND_MILLIS;
                break;

            case MINUTE:
                millis = MINUTE_MILLIS;
                break;

            case HOUR:
                millis = HOUR_MILLIS;
                break;

            case DAY:
                millis = DAY_MILLIS;
                break;

            case WEEK:
                millis = WEEK_MILLIS;
                break;
        }

        return millis;
    }

}
