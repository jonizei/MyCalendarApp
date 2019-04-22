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

/**
 * This class is repository for events and it communicates with the database
 */
public class EventRepository {

    /**
     * Classe's tag used for debugging
     */
    private static final String TAG = "EventRepository";

    /**
     * EventRepository instance
     */
    private static EventRepository repository;

    /**
     * EventDatabase
     */
    private EventDatabaseAdapter eventDatabaseAdapter;

    /**
     * Application context
     */
    private static Context context;

    /**
     * List of all the events
     */
    private List<Event> eventList;

    /**
     * Constructor for the EventRepository.
     * Initializes EventDatabaseAdapter and eventList.
     */
    private EventRepository() {
        eventDatabaseAdapter = new EventDatabaseAdapter(context);

        eventList = eventDatabaseAdapter.getAllEvents();
    }

    /**
     * Returns EventRepository instance if it has already been initialized
     *
     * @return EventRepository instance
     */
    public static EventRepository getInstance() {
        if(repository == null) {
            repository = new EventRepository();
        }

        return repository;
    }

    /**
     * Set application context
     *
     * @param ctx Application context
     */
    public static void setContext(Context ctx) {
        context = ctx;
    }

    /**
     * Saves event to database and add it to the eventList
     *
     * @param event Event to be saved
     */
    public void save(Event event) {
        long tmpId = eventDatabaseAdapter.insertEvent(event);
        if(tmpId != -1) {
            event.setId(tmpId);
            eventList.add(event);
        }
    }

    /**
     * Updates event's values to the database
     *
     * @param event Event to be updated
     */
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

    /**
     * Returns all events
     *
     * @return List of Events
     */
    public List<Event> fetchAll() {
        return eventList;
    }

    /**
     * Deletes event from the eventList and the database
     *
     * @param event Event to be deleted
     */
    public void delete(Event event) {
        eventList.remove(event);
        eventDatabaseAdapter.deleteEvent(event.getId());
    }

    /**
     * Tries to find event from the eventList by its id
     *
     * @param id Events id
     * @return Event object which can be null if not found from the eventList
     */
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

    /**
     * Tries to find events from the eventList by their name
     *
     * @param name Events name
     * @return List of Events which contains all founded events
     */
    public List<Event> findByName(String name) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {
            if(name.equals(event.getName())) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    /**
     * Tries to find events from the eventList by their date
     *
     * @param date Events date
     * @return List of Events which contains founded events
     */
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

    /**
     * Tries to found events from the eventList by their time
     *
     * @param time Events time
     * @return List of Event which contains founded events
     */
    public List<Event> findByTime(LocalTime time) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {
            if(time.equals(event.getTime())) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    /**
     * Closes EventRepository and EventDatabaseAdapter
     */
    public void close() {
        eventDatabaseAdapter.close();
    }

}
