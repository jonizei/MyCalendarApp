package fi.tuni.mycalendarapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventActivity extends AppCompatActivity {

    private final String TAG = "EventActivity";

    private EventRepository eventRepository;

    private EditText inputName;
    private EditText inputDesc;
    private EditText inputDate;
    private EditText inputTime;

    private Dialog dateDialog;
    private Dialog timeDialog;

    private enum DialogType {
        DATE,
        TIME
    }

    private Date eventDate;
    private Time eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        if(extras != null) {
            eventDate = (Date) extras.get("date");
        } else {
            eventDate = new Date();
        }

        eventRepository = EventRepository.getInstance();

        eventTime = new Time(9,0);

        inputName = (EditText) findViewById(R.id.inputEventName);
        inputDesc = (EditText) findViewById(R.id.inputEventDesc);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText) findViewById(R.id.inputTime);

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

    }

    public void pickDate() {
        Debug.printConsole(TAG, "pickDate", "pickDate called", 1);
        dateDialog = createDialog(DialogType.DATE);
        dateDialog.show();
    }

    public void pickTime() {
        Debug.printConsole(TAG, "pickTime", "pickTime called", 1);
        timeDialog = createDialog(DialogType.TIME);
        timeDialog.show();
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
        }

        return builder.create();
    }

    public void saveEvent(View v) {
        Event tmpEvent = new Event();
        tmpEvent.setName(inputName.getText().toString());
        tmpEvent.setDescription(inputDesc.getText().toString());
        tmpEvent.setDate(eventDate);
        tmpEvent.setTime(eventTime);
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
