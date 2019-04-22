package fi.tuni.mycalendarapp;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventRepository {

    private static final String TAG = "EventRepository";

    private static EventRepository repository;
    private EventDatabaseAdapter eventDatabaseAdapter;
    private static Context context;

    private List<Event> eventList;

    private EventRepository() {
        eventDatabaseAdapter = new EventDatabaseAdapter(context);

        /*
        eventDatabaseAdapter.insertEvent(new Event("Test", "Desc", new Date(), new Time(), new EventType()));
        eventDatabaseAdapter.insertEvent(new Event("Test2", "Desc2", new Date(), new Time(), new EventType("Urgent", "#f53c14")));
        eventDatabaseAdapter.insertEvent(new Event("Test3", "Desc3", new Date(), new Time(), new EventType("Important", "#fc890e")));
        eventDatabaseAdapter.insertEvent(new Event("Test4", "Desc4", new Date(), new Time(), new EventType("No hurry", "#1ee315")));
         */

        eventList = eventDatabaseAdapter.getAllEvents();
    }

    public static EventRepository getInstance() {
        if(repository == null) {
            repository = new EventRepository();
        }

        return repository;
    }

    public static void setContext(Context ctx) {
        context = ctx;
    }

    public void save(Event event) {
        long tmpId = eventDatabaseAdapter.insertEvent(event);
        if(tmpId != -1) {
            event.setId(tmpId);
            eventList.add(event);
        }
    }

    public void update(Event event) {

        Event tmpEvent = findById(event.getId());
        Debug.printConsole(TAG, "update", "EventId: " + event.getId(), 1);

        if(tmpEvent != null) {
            tmpEvent.setName(event.getName());
            tmpEvent.setDescription(event.getDescription());
            tmpEvent.setDate(event.getDate());
            tmpEvent.setTime(event.getTime());
            tmpEvent.setEventType(event.getEventType());
            tmpEvent.setNotificationId(event.getNotificationId());
            eventDatabaseAdapter.update(event);
        }

    }

    public List<Event> fetchAll() {
        return eventList;
    }

    public void delete(Event event) {
        eventList.remove(event);
        eventDatabaseAdapter.deleteEvent(event.getId());
    }

    public Event findById(long id) {

        Event tmpEvent = null;

        for(Event event : eventList) {
            if(id == event.getId()) {
                tmpEvent = event;
                break;
            }
        }

        return tmpEvent;
    }

    public List<Event> findByName(String name) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {
            if(name.equals(event.getName())) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    public List<Event> findByDate(LocalDate date) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {

            Debug.printConsole(TAG, "findByDate", "LocalDate: " + date, 2);
            Debug.printConsole(TAG, "findByDate", "EventDate: " + event.getDate(), 2);

            if(event.getDate().equals(date)) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    public List<Event> findByTime(LocalTime time) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {
            if(time.equals(event.getTime())) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    public void close() {
        eventDatabaseAdapter.close();
    }

}
