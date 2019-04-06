package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private final String TAG = "EventActivity";

    private EventRepository eventRepository;

    private EditText inputName;
    private EditText inputDesc;
    private EditText inputDate;
    private EditText inputTime;
    private TextView showColor;
    private Button inputColor;

    private Dialog dialog;

    private enum DialogType {
        DATE,
        TIME,
        COLOR
    }

    private Date eventDate;
    private Time eventTime;
    private EventType eventType;

    public enum Mode {
        CREATE,
        EDIT
    }

    private Mode currentMode = Mode.CREATE;

    private Event eventObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();

        Debug.printConsole(TAG, "onCreate", "onCreate called", 1);

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        if(extras != null) {
            //eventDate = (Date) extras.get("date");
            eventDate = new Date();
        } else {
            eventDate = new Date();
        }

        eventRepository = EventRepository.getInstance();

        inputName = (EditText) findViewById(R.id.inputEventName);
        inputDesc = (EditText) findViewById(R.id.inputEventDesc);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText) findViewById(R.id.inputTime);
        showColor = (TextView) findViewById(R.id.showColor);
        inputColor = (Button) findViewById(R.id.inputColor);

        eventTime = new Time(9,0);
        eventType = new EventType();

        /*
        inputName.setText(eventObject.getName());
            inputDesc.setText(eventObject.getDescription());
            eventDate = eventObject.getDate();
            eventTime = eventObject.getTime();
            eventType = eventObject.getEventType();
            showColor.setBackgroundColor(Color.parseColor(eventObject.getEventType().getColorCode()));
            inputColor.setText(eventObject.getEventType().getName());
         */

        inputDate.setText(f.format(eventDate));
        inputTime.setText(eventTime.toString());
        setListeners();
    }

    private void setListeners() {

        inputDate.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if(hasFocus) {
                pickDate();
                inputDate.clearFocus();
            }
        });

        inputTime.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if(hasFocus) {
                pickTime();
                inputTime.clearFocus();
            }
        });

        inputColor.setOnClickListener((View v) -> {
            pickColor();
        });

    }

    public void pickDate() {
        Debug.printConsole(TAG, "pickDate", "pickDate called", 1);
        dialog = createDialog(DialogType.DATE);
        dialog.show();
    }

    public void pickTime() {
        Debug.printConsole(TAG, "pickTime", "pickTime called", 1);
        dialog = createDialog(DialogType.TIME);
        dialog.show();
    }

    public void pickColor() {
        Debug.printConsole(TAG, "pickColor", "pickColor called", 1);
        dialog = createDialog(DialogType.COLOR);
        dialog.show();
    }

    public Dialog createDialog(DialogType type) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(type == DialogType.DATE) {

            View v = LayoutInflater.from(this).inflate(R.layout.dialog_datepicker, null);

            DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePick);

            builder.setTitle("Pick Date:");
            builder.setView(v);
            builder.setPositiveButton("Ok", (DialogInterface dialog, int id) -> {
                int year = datePicker.getYear();
                int mon = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                eventDate = new GregorianCalendar(year, mon, day).getTime();
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                inputDate.setText(f.format(eventDate));
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                dialog.dismiss();
            });
        } else if(type == DialogType.TIME) {
            View v = LayoutInflater.from(this).inflate(R.layout.dialog_timepicker, null);

            TimePicker timePicker = (TimePicker) v.findViewById(R.id.timePick);

            builder.setTitle("Pick Time:");
            builder.setView(v);
            builder.setPositiveButton("Ok", (DialogInterface dialog, int id) -> {
                int hours = timePicker.getHour();
                int minutes = timePicker.getMinute();
                eventTime = new Time(hours, minutes);
                inputTime.setText(eventTime.toString());
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                dialog.dismiss();
            });
        } else if(type == DialogType.COLOR) {

            builder.setTitle("Choose color:");

            builder.setItems(new String[]{"Default", "Urgent", "Important", "No hurry"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (which) {

                        case 0:
                            eventType.setColorCode("#ffffff");
                            eventType.setName("Default");
                            break;

                        case 1:
                            eventType.setColorCode("#f53c14");
                            eventType.setName("Urgent");
                            break;

                        case 2:
                            eventType.setColorCode("#fc890e");
                            eventType.setName("Important");
                            break;

                        case 3:
                            eventType.setColorCode("#1ee315");
                            eventType.setName("No hurry");
                            break;
                    }

                    showColor.setBackgroundColor(Color.parseColor(eventType.getColorCode()));
                    inputColor.setText(eventType.getName());

                }
            });

        }

        return builder.create();
    }

    public void saveEvent(View v) {
        Event tmpEvent = new Event();
        tmpEvent.setName(inputName.getText().toString());
        tmpEvent.setDescription(inputDesc.getText().toString());
        tmpEvent.setDate(eventDate);
        tmpEvent.setTime(eventTime);
        tmpEvent.setEventType(eventType);
        eventRepository.save(tmpEvent);
        goBack(true);
    }

    private void goBack(boolean status) {
        Intent i = new Intent();
        i.putExtra("status", status);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack(false);
        super.onBackPressed();
    }

}
