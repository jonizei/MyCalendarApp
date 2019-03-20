package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 1;

    private EventRepository eventRepository;

    private TextView txtTitle;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Debug.loadDebug(this);

        eventRepository = EventRepository.getInstance();

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);

        showToday();
    }

    private void showToday() {

        List<Event> eventsToday = eventRepository.findByDate(new Date());

        if(eventsToday.size() > 0) {

            txtContent.setText("");

            for(Event event : eventsToday) {
                txtContent.append(event.toString() + "\n");
            }
        } else {
            txtContent.setText("No events");
        }

    }

    public void addEvent(View v) {
        Intent i = new Intent(this, EventActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        showToday();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            boolean status = data.getBooleanExtra("status", false);

            if(status) {
                Toast.makeText(this,"Event saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Event was not saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
