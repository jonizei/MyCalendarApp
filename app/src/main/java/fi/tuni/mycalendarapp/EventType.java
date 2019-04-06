package fi.tuni.mycalendarapp;

public class EventType {
    private String name;
    private String colorCode;

    public EventType() {
        name = "Default";
        colorCode = "#ffffff";
    }

    public EventType(String name, String colorCode) {
        this.name = name;
        this.colorCode = colorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
