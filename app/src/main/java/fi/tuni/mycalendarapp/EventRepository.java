package fi.tuni.mycalendarapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventRepository {

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
        eventList.add(new Event("Testi", "", new Date(), new Time(9, 0)));
        eventList.add(new Event("Testi", "", new Date(), new Time(9, 0)));
        eventList.add(new Event("Testi", "", new Date(), new Time(9, 0)));
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
            if(name == event.getName()) {
                tmpEventList.add(event);
            }
        }

        return tmpEventList;
    }

    public List<Event> findByDate(Date date) {

        List<Event> tmpEventList = new ArrayList<>();

        for(Event event : eventList) {
            if(date.equals(date)) {
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

}
