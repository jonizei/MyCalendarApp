package fi.tuni.mycalendarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDatabaseAdapter {

    private final static String TAG = "EventDatabaseAdapter";

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS EVENT_DATA(ID integer primary key autoincrement, NAME text, DESCRIPTION text, DATE text, TIME text, LABEL_NAME text, LABEL_COLOR text);";
    public static final String DATABASE_REMOVE = "DROP TABLE IF EXISTS eventData;";

    public static final String SELECT_ALL_EVENTS = "SELECT * FROM EVENT_DATA";
    public static final String DELETE_ALL_EVENTS = "DELETE FROM EVENT_DATA";

    private static final String QUERY_OK = "OK";
    private static final String QUERY_FAILED = "FAILED";

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static SQLiteDatabase database;

    private final Context context;

    private static MyDatabaseHelper dbHelper;

    public EventDatabaseAdapter(Context context) {
        this.context = context;
        dbHelper = new MyDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public EventDatabaseAdapter open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        database.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return database;
    }

    public long insertEvent(Event event) {

        long result = -1;

        try {

            ContentValues newValues = new ContentValues();
            newValues.put("NAME", event.getName());
            newValues.put("DESCRIPTION", event.getDescription());
            newValues.put("DATE", dateFormat.format(event.getDate()));
            newValues.put("TIME", event.getTime().toString());
            newValues.put("LABEL_NAME", event.getEventType().getName());
            newValues.put("LABEL_COLOR", event.getEventType().getColorCode());

            database = dbHelper.getWritableDatabase();
            result = database.insert("EVENT_DATA", null, newValues);
            Debug.printConsole(TAG, "insertEvent", "Query result: " + result, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void deleteEvent(long eventId) {
        database = dbHelper.getWritableDatabase();
        String where = "ID=?";
        int deletedEntries = database.delete("EVENT_DATA", where, new String[]{"" + eventId});
    }

    public void deleteAllEvents() {
        database = dbHelper.getWritableDatabase();
         database.delete("EVENT_DATA", null, null);
    }

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
            Date tmpDate = new Date();

            try {
                tmpDate = dateFormat.parse(c.getString(c.getColumnIndex("DATE")));
            } catch (Exception e) {
                e.printStackTrace();
            }

            tmpEvent.setDate(tmpDate);
            Time tmpTime = Time.parse(c.getString(c.getColumnIndex("TIME")));
            tmpEvent.setTime(tmpTime);

            EventType tmpEventType = new EventType();
            tmpEventType.setName(c.getString(c.getColumnIndex("LABEL_NAME")));
            tmpEventType.setColorCode(c.getString(c.getColumnIndex("LABEL_COLOR")));

            tmpEvent.setEventType(tmpEventType);
            eventList.add(tmpEvent);
            //Debug.printConsole(TAG, "getAllEvents", "Date: " + c.getString(c.getColumnIndex("DATE")), 1);
        }

        return eventList;
    }

}
