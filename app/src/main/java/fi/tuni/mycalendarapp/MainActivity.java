package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 1;

    private EventRepository eventRepository;

    private CalendarView calendarView;
    private TextView txtTitle;
    private TextView txtContent;

    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.loadDebug(this);

        eventRepository.setContext(this);
        eventRepository = EventRepository.getInstance();
        selectedDate = new Date();

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);

        setupListeners();
        updateEventInfo(new Date());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

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

    private void setupListeners() {

        calendarView.setOnDateChangeListener((@NonNull CalendarView view, int year, int month, int dayOfMonth) -> {
            Debug.printConsole(TAG, "setupListeners", "Day: " + dayOfMonth, 3);
            selectedDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
            updateEventInfo(selectedDate);
        });

    }

    private void updateEventInfo(Date date) {

        List<Event> eventsByDate = eventRepository.findByDate(date);

        Debug.printConsole(TAG, "updateEventInfo", "eventsByDate: " + eventsByDate.size(), 1);

        if(eventsByDate.size() > 0) {

            txtContent.setText("");

            for(Event event : eventsByDate) {
                txtContent.append(event.toString() + "\n");
            }
        } else {
            txtContent.setText("No events");
        }

    }

    public void addEvent() {
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("mode", "create");
        i.putExtra("date", selectedDate);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateEventInfo(new Date());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            boolean status = data.getBooleanExtra("status", false);

            if(status) {
                Toast.makeText(this,"Event saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Event was not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void viewEvents(View v) {
        Intent i = new Intent(this, EventListActivity.class);
        i.putExtra("date", selectedDate);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventRepository.close();
    }
}
