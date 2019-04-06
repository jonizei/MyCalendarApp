package fi.tuni.mycalendarapp;

public class Time {

    private int hours;
    private int minutes;

    public Time() {

    }

    public Time(int hours, int minutes) {
        setHours(hours);
        setMinutes(minutes);
    }

    public void setHours(int hours) {
        if(hours <= 23) {
            this.hours = hours;
        }
    }

    public void setMinutes(int minutes) {
        if(minutes <= 59) {
            this.minutes = minutes;
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean equals(Time time) {
        if(time.getHours() == hours && time.getMinutes() == minutes) {
            return true;
        }

        return false;
    }

    public String toString() {

        String strMin = "" + minutes;
        String strHour = "" + hours;

        if(minutes < 10) {
            strMin = "0" + minutes;
        }

        if(hours < 10) {
            strHour = "0" + hours;
        }

        return strHour + ":" + strMin;
    }

    public static Time parse(String s) {
        Time time = new Time();
        String[] tokens = s.split(":");

        if(tokens.length == 2) {
            time.setHours(parseStr(tokens[0]));
            time.setMinutes(parseStr(tokens[1]));
        }

        return time;
    }

    private static int parseStr(String s) {

        int value = 0;

        if(s.equals("00")) {
            value = 0;
        }
        else if(s.charAt(0) == '0' && s.charAt(1) != '0') {
            value = Integer.parseInt("" + s.charAt(1));
        }
        else {
            value = Integer.parseInt(s);
        }

        return value;
    }
}
