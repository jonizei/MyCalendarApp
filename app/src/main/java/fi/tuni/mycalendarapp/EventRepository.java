package fi.tuni.mycalendarapp;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
            eventDatabaseAdapter.update(event);
        }

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

    public List<Event> findByDate(Date date) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {

            if(compareDates(event.getDate(), date)) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    public List<Event> findByTime(Time time) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {
            if(time.equals(event.getTime())) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    private boolean compareDates(Date d1, Date d2) {

        Date a = getZeroTimeDate(d1);
        Date b = getZeroTimeDate(d2);

        if(a.compareTo(b) == 0) {
            return true;
        }

        return false;
    }

    private Date getZeroTimeDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();

        return date;
    }

    public void close() {
        eventDatabaseAdapter.close();
    }

}
