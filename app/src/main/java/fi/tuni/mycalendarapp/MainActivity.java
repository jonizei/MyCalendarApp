package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * This class is the main activity
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Classe's tag used for debugging
     */
    private final String TAG = "MainActivity";

    /**
     * Integer for identify event creation result
     */
    private static final int REQUEST_CODE_CREATE = 1;

    /**
     * Integer for identify event modification result
     */
    private static final int REQUEST_CODE_EDIT = 2;

    /**
     * EventRepository object
     */
    private EventRepository eventRepository;

    /**
     * Field that shows week number
     */
    private TextView txtWeekNumber;

    /**
     * CalendarView object
     */
    private CalendarView calendarView;

    /**
     * ListView object
     */
    private ListView listDailyEvents;

    /**
     * Selected date in calendarView
     */
    private LocalDate selectedDate;

    /**
     * Selected week in calendarView
     */
    private int selectedWeek;

    /**
     * This method initializes all input field values and default values
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.loadDebug(this);

        eventRepository.setContext(this);
        eventRepository = EventRepository.getInstance();

        selectedDate = LocalDate.now();

        txtWeekNumber = (TextView) findViewById(R.id.txtWeekNumber);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        listDailyEvents = (ListView) findViewById(R.id.listDailyEvents);

        setupListeners();
        updateEventInfo(selectedDate);
        updateWeekNumber(selectedDate);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Creates options menu
     *
     * @param menu Menu object
     * @return boolean that tells the outcome
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    /**
     * Triggers if item in options is clicked
     *
     * @param item Clicked menu item
     * @return boolean that tells the outcome
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case (R.id.addEvent):
                addEvent();
                break;
        }

        return true;
    }

    /**
     * Sets listener for calendarView
     */
    private void setupListeners() {

        calendarView.setOnDateChangeListener((@NonNull CalendarView view, int year, int month, int dayOfMonth) -> {
            Debug.printConsole(TAG, "setupListeners", "Month: " + month, 3);
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
            Debug.printConsole(TAG, "setOnChangeListener", "Changed: " + selectedDate.toString(), 2);
            updateEventInfo(selectedDate);
            updateWeekNumber(selectedDate);
        });

    }

    /**
     * Updates week number depending on the date
     *
     * @param date Selected date
     */
    private void updateWeekNumber(LocalDate date) {
        TemporalField woy = WeekFields.of(Locale.GERMAN).weekOfWeekBasedYear();
        selectedWeek = date.get(woy);
        Debug.printConsole(TAG, "updateWeekNumber", "WeekNumber: " + selectedWeek, 1);
        txtWeekNumber.setText("Week: " + selectedWeek);
    }

    /**
     * Updates Daily event list depending on the date
     *
     * @param date Selected date
     */
    private void updateEventInfo(LocalDate date) {

        List<Event> eventsByDate = eventRepository.findByDate(date);

        DailyEventsAdapter adapter = new DailyEventsAdapter(this, eventsByDate);
        listDailyEvents.setAdapter(adapter);

        listDailyEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event tmpEvent = (Event) parent.getItemAtPosition(position);
                editEvent(tmpEvent);
            }
        });

        Debug.printConsole(TAG, "updateEventInfo", "eventsByDate: " + eventsByDate.size(), 1);

    }

    /**
     * Opens event activity in create mode
     */
    public void addEvent() {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("mode", "create");
        i.putExtra("date", selectedDate);
        startActivityForResult(i, REQUEST_CODE_CREATE);
    }

    /**
     * Opens event activity in edit mode
     *
     * @param event Event to be modified
     */
    public void editEvent(Event event) {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("mode", "edit");
        i.putExtra("event", event);
        startActivityForResult(i, REQUEST_CODE_EDIT);
    }

    /**
     * Triggers when user returs to the activity
     * and updates daily event list
     */
    @Override
    public void onResume() {
        super.onResume();
        updateEventInfo(selectedDate);
    }

    /**
     * Get results from result activities and separates results with requestCode
     *
     * @param requestCode Identifies the request
     * @param resultCode Status for the result
     * @param data Intent that contains data from result activity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            boolean status = data.getBooleanExtra("status", false);

            if(requestCode == REQUEST_CODE_CREATE) {

                if(status) {
                    Toast.makeText(this,"Event saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Event was not saved", Toast.LENGTH_SHORT).show();
                }

            } else if(requestCode == REQUEST_CODE_EDIT) {

                if(status) {
                    Toast.makeText(this,"Event modified successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Event was not modified", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    /**
     * Opens EventListActivity
     *
     * @param v Current activity
     */
    public void viewEvents(View v) {
        Intent i = new Intent(this, EventListActivity.class);
        i.putExtra("date", selectedDate);
        startActivity(i);
    }

    /**
     * Triggers when class is being destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        eventRepository.close();
    }
}
