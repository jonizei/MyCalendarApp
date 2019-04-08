package fi.tuni.mycalendarapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class DailyEventsAdapter extends ArrayAdapter<Event> {

    public DailyEventsAdapter(Context context, List<Event> eventList) {
        super(context, 0, eventList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.daily_event_list_item, parent, false);
        }

        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);
        TextView txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
        TextView txtEventDesc = (TextView) convertView.findViewById(R.id.txtEventDesc);
        TextView txtTypeColor = (TextView) convertView.findViewById(R.id.txtTypeColor);

        txtTime.setText(event.getTime().toString());
        txtEventName.setText(event.getName());
        txtEventDesc.setText(event.getDescription());
        txtTypeColor.setBackgroundColor(Color.parseColor(event.getEventType().getColorCode()));

        return convertView;
    }

}
