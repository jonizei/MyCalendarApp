package fi.tuni.mycalendarapp;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used for handling the database
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class EventDatabaseAdapter {

    /**
     * Classes tag for debugging
     */
    private final static String TAG = "EventDatabaseAdapter";

    /**
     * Database name
     */
    private static final String DATABASE_NAME = "event_database.db";

    /**
     * Database version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Database create table SQL query
     */
    public static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS EVENT_DATA(ID integer primary key autoincrement, NAME text, DESCRIPTION text, DATE text, TIME text, LABEL_NAME text, LABEL_COLOR text, N_ID integer);";

    /**
     * Database drop table SQL query
     */
    public static final String DATABASE_REMOVE = "DROP TABLE EVENT_DATA;";

    /**
     * Database select all from table SQL query
     */
    public static final String SELECT_ALL_EVENTS = "SELECT * FROM EVENT_DATA";

    /**
     * Database delete all from table SQL query
     */
    public static final String DELETE_ALL_EVENTS = "DELETE FROM EVENT_DATA";

    /**
     * SQLiteDatabase object
     */
    public static SQLiteDatabase database;

    /**
     * Application context
     */
    private final Context context;

    /**
     * MyDatabaseHelper object
     */
    private static MyDatabaseHelper dbHelper;

    /**
     * Constructor for EventDatabaseAdapter
     *
     * @param context Application context
     */
    public EventDatabaseAdapter(Context context) {
        this.context = context;
        dbHelper = new MyDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Closes database connection
     */
    public void close() {
        database.close();
    }

    /**
     * Returns database instance
     *
     * @return SqlLiteDatabase instance
     */
    public SQLiteDatabase getDatabaseInstance() {
        return database;
    }

    /**
     * Inserts event into the database
     *
     * @param event Event object
     * @return long last inserted event id
     */
    public long insertEvent(Event event) {

        long result = -1;

        try {

            ContentValues newValues = new ContentValues();
            newValues.put("NAME", event.getName());
            newValues.put("DESCRIPTION", event.getDescription());
            newValues.put("DATE", event.getDate().toString());
            newValues.put("TIME", event.getTime().toString());
            newValues.put("LABEL_NAME", event.getEventType().getName());
            newValues.put("LABEL_COLOR", event.getEventType().getColorCode());
            newValues.put("N_ID", event.getNotificationId());

            database = dbHelper.getWritableDatabase();
            result = database.insert("EVENT_DATA", null, newValues);
            Debug.printConsole(TAG, "insertEvent", "Query result: " + result, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Deletes event by id
     *
     * @param eventId Id of the event
     */
    public void deleteEvent(long eventId) {
        database = dbHelper.getWritableDatabase();
        String where = "ID=?";
        int deletedEntries = database.delete("EVENT_DATA", where, new String[]{"" + eventId});
    }

    /**
     * Deletes all events from the table
     */
    public void deleteAllEvents() {
        database = dbHelper.getWritableDatabase();
         database.delete("EVENT_DATA", null, null);
    }

    /**
     * Fetches all events from the database, puts the to a list
     * and returns them
     *
     * @return list which contains all events
     */
    public List<Event> getAllEvents() {

        Debug.printConsole(TAG, "getAllEvents", "fetching events", 1);

        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery(SELECT_ALL_EVENTS, null);

        List<Event> eventList = new ArrayList<>();

        while(c.moveToNext()) {
            Event tmpEvent = new Event();
            tmpEvent.setId(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
            tmpEvent.setName(c.getString(c.getColumnIndex("NAME")));
            tmpEvent.setDescription(c.getString(c.getColumnIndex("DESCRIPTION")));
            LocalDate tmpDate = LocalDate.now();

            try {
                tmpDate = LocalDate.parse(c.getString(c.getColumnIndex("DATE")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            tmpEvent.setDate(tmpDate);
            LocalTime tmpTime = LocalTime.parse(c.getString(c.getColumnIndex("TIME")));
            tmpEvent.setTime(tmpTime);

            EventType tmpEventType = new EventType();
            tmpEventType.setName(c.getString(c.getColumnIndex("LABEL_NAME")));
            tmpEventType.setColorCode(c.getString(c.getColumnIndex("LABEL_COLOR")));

            tmpEvent.setEventType(tmpEventType);

            tmpEvent.setNotificationId(Integer.parseInt(c.getString(c.getColumnIndex("N_ID"))));
            //Debug.printConsole(TAG, "getAllEvents", "N_ID: " + c.getString(7), 2);

            eventList.add(tmpEvent);
            //Debug.printConsole(TAG, "getAllEvents", "Date: " + c.getString(c.getColumnIndex("DATE")), 1);
        }

        return eventList;
    }

    /**
     * Updates existing event's values
     *
     * @param event Event to be modified
     */
    public void update(Event event) {

        ContentValues newValues = new ContentValues();
        newValues.put("NAME", event.getName());
        newValues.put("DESCRIPTION", event.getDescription());
        newValues.put("DATE", event.getDate().toString());
        newValues.put("TIME", event.getTime().toString());
        newValues.put("LABEL_NAME", event.getEventType().getName());
        newValues.put("LABEL_COLOR", event.getEventType().getColorCode());
        newValues.put("N_ID", event.getNotificationId());

        String where = "ID=?";
        database = dbHelper.getWritableDatabase();
        database.update("EVENT_DATA", newValues, where, new String[]{"" + event.getId()});

    }

}
