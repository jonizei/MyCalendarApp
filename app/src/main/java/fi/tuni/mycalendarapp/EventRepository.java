package fi.tuni.mycalendarapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventRepository {

    private static final String TAG = "EventRepository";

    private static EventRepository repository;

    public enum EventProperty {
        NAME,
        DESCRIPTION,
        DATE,
        TIME
    }

    private List<Event> eventList;

    private EventRepository() {
        eventList = loadEvents();
        eventList.add(new Event("Test", "Desc", new Date(), new Time(9, 0)));
        eventList.add(new Event("Test2", "Desc2", new Date(), new Time(10, 0)));
        eventList.add(new Event("Test3", "Desc3", new Date(), new Time(11, 0)));
    }

    public static EventRepository getInstance() {
        if(repository == null) {
            repository = new EventRepository();
        }

        return repository;
    }

    private List<Event> loadEvents() {
        return new ArrayList<>();
    }

    public void save(Event event) {
        eventList.add(event);
    }

    public void update(Event event, EventProperty property) {

        Event tmpEvent = findById(event.getId());

        switch (property) {
            case NAME:
                tmpEvent.setName(event.getName());
                break;

            case DESCRIPTION:
                tmpEvent.setDescription(event.getDescription());
                break;

            case DATE:
                tmpEvent.setDate(event.getDate());
                break;

            case TIME:
                tmpEvent.setTime(event.getTime());
                break;
        }

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

}
