package fi.tuni.mycalendarapp;

/**
 * This class represents label of events urgency
 */
public class EventType {

    /**
     * Text on the label
     */
    private String name;

    /**
     * Label background color
     */
    private String colorCode;

    /**
     * Constructor for EventType which initializes default values for name and colorCode
     */
    public EventType() {
        name = "Default";
        colorCode = "#ffffff";
    }

    /**
     * Constructor for EventType
     *
     * @param name EventType's name
     * @param colorCode EventType's color
     */
    public EventType(String name, String colorCode) {
        this.name = name;
        this.colorCode = colorCode;
    }

    /**
     * Returns name
     *
     * @return EventType's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets value for name
     *
     * @param name EventType's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns colorCode
     *
     * @return EventType's colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets value for colorCode
     *
     * @param colorCode EventType's colorCode
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
