package fi.tuni.mycalendarapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventTypesAdapter extends ArrayAdapter<EventType> {

    public EventTypesAdapter(Context context, List<EventType> eventTypeList) {
        super(context, 0, eventTypeList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EventType eventType = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_type_item, parent, false);
        }

        TextView txtTypeColor = (TextView) convertView.findViewById(R.id.txtTypeColor);
        TextView txtTypeName = (TextView) convertView.findViewById(R.id.txtTypeName);

        txtTypeColor.setBackgroundColor(Color.parseColor(eventType.getColorCode()));
        txtTypeName.setText(eventType.getName());

        return convertView;
    }

}
