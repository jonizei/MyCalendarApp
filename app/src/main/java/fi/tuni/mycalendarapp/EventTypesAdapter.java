package fi.tuni.mycalendarapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This class is used for showing EventTypes in a list view
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class EventTypesAdapter extends ArrayAdapter<EventType> {

    /**
     * Constructor for EventTypesAdapter
     *
     * @param context Application context
     * @param eventTypeList List of eventTypes which will be shown in the list
     */
    public EventTypesAdapter(Context context, List<EventType> eventTypeList) {
        super(context, 0, eventTypeList);
    }

    /**
     * This method builds the single eventType layout
     *
     * @param position EventType object position in the list
     * @param convertView Layout view
     * @param parent ListView which holds all the views
     * @return Layout view
     */
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
