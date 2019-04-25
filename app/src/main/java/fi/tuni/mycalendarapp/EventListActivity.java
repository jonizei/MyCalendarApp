package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class is used for showing events in a list view
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class EventListActivity extends AppCompatActivity {

    /**
     * Classe's tag
     */
    private static final String TAG = "EventListActivity";

    /**
     * Request code for the resultActivity
     */
    private static final int REQUEST_CODE = 1;

    /**
     * EventRepository object
     */
    private EventRepository eventRepository;

    /**
     * Date which determines which events needs to be shown
     */
    private LocalDate selectedDate;

    /**
     * Initializes the EventListActivity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Bundle extras = getIntent().getExtras();

        selectedDate = LocalDate.now();

        if(extras != null) {
            selectedDate = (LocalDate) extras.get("date");
        }

        EventRepository.setContext(this);
        eventRepository = EventRepository.getInstance();

        updateList();

    }

    /**
     * Updates the list view
     */
    public void updateList() {

        List<Event> eventList = eventRepository.findByDate(selectedDate);

        Collections.sort(eventList);

        EventsAdapter adapter = new EventsAdapter(this, eventList);

        ListView eventListView = (ListView) findViewById(R.id.eventListView);
        eventListView.setAdapter(adapter);

    }

    /**
     * Opens EventActivity in Edit mode
     *
     * @param event Event to be modified
     */
    public void editEvent(Event event) {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("mode", "edit");
        i.putExtra("event", event);
        startActivityForResult(i, REQUEST_CODE);
    }

    /**
     * Gets result from resultActivities
     *
     * @param requestCode integer that shows which request code was used
     * @param resultCode integers that shows the status
     * @param data Intent which contains data from resultActivity
     */
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

    /**
     * Triggers when user returns to this activity and updates the list view
     */
    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

}
