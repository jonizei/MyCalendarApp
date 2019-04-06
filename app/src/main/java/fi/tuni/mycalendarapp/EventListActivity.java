package fi.tuni.mycalendarapp;

import android.content.Intent;
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
    private static final int REQUEST_CODE = 1;

    private EventRepository eventRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventRepository = EventRepository.getInstance();

        updateList();

    }

    public void updateList() {

        List<Event> eventList = eventRepository.findByDate(new Date());

        EventsAdapter adapter = new EventsAdapter(this, eventList);

        ListView eventListView = (ListView) findViewById(R.id.eventListView);
        eventListView.setAdapter(adapter);

    }

    public void editEvent(Event event) {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("mode", "edit");



        try {
            startActivityForResult(i, REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

}
