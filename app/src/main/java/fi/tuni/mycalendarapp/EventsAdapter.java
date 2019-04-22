package fi.tuni.mycalendarapp;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * This is an adapter that is used in a list view to show custom layout
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class EventsAdapter extends ArrayAdapter<Event> {

    /**
     * Classe's tag used in debugging
     */
    private static final String TAG = "EventsAdapter";

    /**
     * EventRepository object
     */
    private EventRepository eventRepository;

    /**
     * EventListActivity object
     */
    private EventListActivity listActivity;

    /**
     * Constructor for EventsAdapter
     *
     * @param listActivity Activity that uses the list
     * @param events List of Events used in the list
     */
    public EventsAdapter(EventListActivity listActivity, List<Event> events) {
        super(listActivity,0, events);
        this.listActivity = listActivity;
    }

    /**
     * This method builds the single event layout
     *
     * @param position Event-object position in the list
     * @param convertView Layout view
     * @param parent ListView which holds all the views
     * @return Layout view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        eventRepository = EventRepository.getInstance();

        Event event = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }

        TextView eventType = (TextView) convertView.findViewById(R.id.eventType);
        TextView eventTime = (TextView) convertView.findViewById(R.id.eventTime);
        TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
        TextView eventDesc = (TextView) convertView.findViewById(R.id.eventDesc);
        ImageButton imgBtnDelete = (ImageButton) convertView.findViewById(R.id.imgBtnDelete);
        ImageButton imgBtnEdit = (ImageButton) convertView.findViewById(R.id.imgBtnEdit);

        imgBtnDelete.setOnClickListener((View v) -> {
            eventRepository.delete(event);
            listActivity.updateList();
        });

        imgBtnEdit.setOnClickListener((View v) -> {
            listActivity.editEvent(event);
        });

        eventType.setText(event.getEventType().getName());
        eventType.setBackgroundColor(Color.parseColor(event.getEventType().getColorCode()));
        //Debug.printConsole(TAG, "getView", "ColorCode: " + event.getEventType().getColorCode(), 1);
        eventTime.setText(event.getTime().toString());
        eventName.setText(event.getName());
        eventDesc.setText(event.getDescription());

        return convertView;
    }

}
