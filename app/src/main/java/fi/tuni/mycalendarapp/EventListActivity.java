package fi.tuni.mycalendarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";

    private EventRepository eventRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventRepository = EventRepository.getInstance();

        List<Event> eventList = eventRepository.findByDate(new Date());

        EventsAdapter adapter = new EventsAdapter(this, eventList);

        ListView eventListView = (ListView) findViewById(R.id.eventListView);
        eventListView.setAdapter(adapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected();
            }
        });

    }

    private void itemSelected() {
        Debug.printConsole(TAG, "itemSelected", "item selected", 1);
    }

}
