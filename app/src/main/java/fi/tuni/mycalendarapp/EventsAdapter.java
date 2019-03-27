package fi.tuni.mycalendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<Event> {

    private EventRepository eventRepository;

    public EventsAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        eventRepository = EventRepository.getInstance();

        Event event = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item, parent, false);
        }

        TextView eventTime = (TextView) convertView.findViewById(R.id.eventTime);
        TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
        TextView eventDesc = (TextView) convertView.findViewById(R.id.eventDesc);
        ImageButton imgBtnDelete = (ImageButton) convertView.findViewById(R.id.imgBtnDelete);

        imgBtnDelete.setOnClickListener((View v) -> {

        });

        eventTime.setText(event.getTime().toString());
        eventName.setText(event.getName());
        eventDesc.setText(event.getDescription());

        return convertView;
    }

    private void deleteEvent() {

    }

}
