package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";
    private static final int REQUEST_CODE = 1;

    private EventRepository eventRepository;
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Bundle extras = getIntent().getExtras();

        selectedDate = new Date();

        if(extras != null) {
            selectedDate = (Date) extras.get("date");
        }

        eventRepository = EventRepository.getInstance();

        updateList();

    }

    public void updateList() {

        List<Event> eventList = eventRepository.findByDate(selectedDate);

        EventsAdapter adapter = new EventsAdapter(this, eventList);

        ListView eventListView = (ListView) findViewById(R.id.eventListView);
        eventListView.setAdapter(adapter);

    }

    public void editEvent(Event event) {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("mode", "edit");
        i.putExtra("event", event);
        startActivityForResult(i, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            boolean status = data.getBooleanExtra("status", false);

            if(status) {
                Toast.makeText(this,"Event modified successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Event was not modified", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

}
