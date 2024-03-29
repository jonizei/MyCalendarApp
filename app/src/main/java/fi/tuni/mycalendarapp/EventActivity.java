package fi.tuni.mycalendarapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.v4.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * This class is used for creating and modifying events
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class EventActivity extends AppCompatActivity {

    /**
     * classes tag used in debugging
     */
    private final String TAG = "EventActivity";

    /**
     * EventRepository object
     */
    private EventRepository eventRepository;

    /**
     * Integer used in reminders
     */
    private static int NOTIFICATION_REQ_CODE = 10;

    /**
     * Integer for identifying notifications
     */
    private static int notificationCounter = 1;

    /**
     * Name input field
     */
    private EditText inputName;

    /**
     * Description input field
     */
    private EditText inputDesc;

    /**
     * Date input field
     */
    private EditText inputDate;

    /**
     * Time input field
     */
    private EditText inputTime;

    /**
     * Field to show color
     */
    private TextView showColor;

    /**
     * Button for choosing color
     */
    private Button inputColor;

    /**
     * Button for saving event
     */
    private Button btnSave;

    /**
     * Input field for reminder
     */
    private EditText inputReminder;

    /**
     * Dialog object
     */
    private Dialog dialog;

    /**
     * Enumerable for identifying dialogs
     */
    private enum DialogType {
        DATE,
        TIME,
        COLOR,
        REMINDER
    }

    /**
     * Event date
     */
    private LocalDate eventDate;

    /**
     * Event time
     */
    private LocalTime eventTime;

    /**
     * Event type
     */
    private EventType eventType;

    /**
     * Time and Date for reminder
     */
    private LocalDateTime remindTime;

    /**
     * Enumerable for identifying is user editing or creating event
     */
    public enum Mode {
        CREATE,
        EDIT
    }

    /**
     * Current mode
     */
    private Mode currentMode = Mode.CREATE;

    /**
     * Event Object
     */
    private Event eventObject = null;

    /**
     * This method initializes all input field values and default values,
     * depending which mode is on
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Bundle extras = getIntent().getExtras();

        Debug.printConsole(TAG, "onCreate", "onCreate called", 1);

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ROOT);

        eventDate = LocalDate.now();

        if(extras != null) {
            if(extras.getString("mode").equals("create")) {
                currentMode = Mode.CREATE;
                eventDate = (LocalDate) extras.get("date");
            } else if(extras.getString("mode").equals("edit")) {
                eventObject = extras.getParcelable("event");
                currentMode = Mode.EDIT;
            }
        }

        eventRepository = EventRepository.getInstance();

        inputName = (EditText) findViewById(R.id.inputEventName);
        inputDesc = (EditText) findViewById(R.id.inputEventDesc);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText) findViewById(R.id.inputTime);
        showColor = (TextView) findViewById(R.id.showColor);
        inputColor = (Button) findViewById(R.id.inputColor);
        btnSave = (Button) findViewById(R.id.btnSave);
        inputReminder = (EditText) findViewById(R.id.inputReminder);

        eventTime = LocalTime.parse("09:00");
        eventType = new EventType();

        if(currentMode == Mode.EDIT) {
            inputName.setText(eventObject.getName());
            inputDesc.setText(eventObject.getDescription());
            eventDate = eventObject.getDate();
            eventTime = eventObject.getTime();
            showColor.setBackgroundColor(Color.parseColor(eventObject.getEventType().getColorCode()));
            inputColor.setText(eventObject.getEventType().getName());
            btnSave.setText("Update");
            eventType = eventObject.getEventType();
        }

        inputDate.setText(eventDate.toString());
        inputTime.setText(eventTime.toString());
        setListeners();
    }

    /**
     * Sets listeners for input fields and buttons
     */
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

        inputReminder.setOnFocusChangeListener((View view, boolean hasFocus) -> {
            if(hasFocus) {
                pickReminder();
                inputReminder.clearFocus();
            }
        });

        inputColor.setOnClickListener((View v) -> {
            pickColor();
        });

        btnSave.setOnClickListener((View v) -> {
            saveEvent();
        });
    }

    /**
     * Opens date picker
     */
    public void pickDate() {
        Debug.printConsole(TAG, "pickDate", "pickDate called", 1);
        dialog = createDialog(DialogType.DATE);
        dialog.show();
    }

    /**
     * Opens time picker
     */
    public void pickTime() {
        Debug.printConsole(TAG, "pickTime", "pickTime called", 1);
        dialog = createDialog(DialogType.TIME);
        dialog.show();
    }

    /**
     * Opens label color picker
     */
    public void pickColor() {
        Debug.printConsole(TAG, "pickColor", "pickColor called", 1);
        dialog = createDialog(DialogType.COLOR);
        dialog.show();
    }

    /**
     * Opens reminder time picker
     */
    public void pickReminder() {
        Debug.printConsole(TAG, "pickReminder", "pickReminder called", 1);
        dialog = createDialog(DialogType.REMINDER);
        dialog.show();
    }

    /**
     * Builds dialog depending on the DialogType
     *
     * @param type Value that determines which dialog will be built
     * @return Dialog object
     */
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
                eventDate = LocalDate.of(year, mon, day);
                inputDate.setText(eventDate.toString());
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                dialog.dismiss();
            });
        } else if(type == DialogType.TIME) {
            View v = LayoutInflater.from(this).inflate(R.layout.dialog_timepicker, null);

            TimePicker timePicker = (TimePicker) v.findViewById(R.id.timePick);

            timePicker.setIs24HourView(true);
            timePicker.setHour(eventTime.getHour());
            timePicker.setMinute(eventTime.getMinute());

            builder.setTitle("Pick Time:");
            builder.setView(v);
            builder.setPositiveButton("Ok", (DialogInterface dialog, int id) -> {
                int hours = timePicker.getHour();
                int minutes = timePicker.getMinute();
                eventTime = LocalTime.of(hours, minutes);
                inputTime.setText(eventTime.toString());
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                dialog.dismiss();
            });
        } else if(type == DialogType.COLOR) {
            View v = LayoutInflater.from(this).inflate(R.layout.dialog_typepicker, null);

            List<EventType> eventTypeList = new ArrayList<>();
            eventTypeList.add(new EventType("Default", "#ffffff"));
            eventTypeList.add(new EventType("Urgent", "#f53c14"));
            eventTypeList.add(new EventType("Important", "#fc890e"));
            eventTypeList.add(new EventType("No hurry", "#1ee315"));

            ListView eventTypeListView = (ListView) v.findViewById(R.id.typePick);

            EventTypesAdapter adapter = new EventTypesAdapter(this, eventTypeList);
            eventTypeListView.setAdapter(adapter);

            eventTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    eventType = (EventType) parent.getItemAtPosition(position);
                }
            });

            builder.setTitle("Choose color:");
            builder.setView(v);


            builder.setPositiveButton("OK", (DialogInterface dialog, int id) -> {
                showColor.setBackgroundColor(Color.parseColor(eventType.getColorCode()));
                inputColor.setText(eventType.getName());
                dialog.dismiss();
            });

            builder.setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                dialog.dismiss();
            });

        } else if(type == DialogType.REMINDER) {

            String[] reminderList = new String[] {
                "No reminder",
                    "5 minutes",
                    "10 minutes",
                    "15 minutes",
                    "30 minutes",
                    "1 hour",
                    "2 hours",
                    "3 hours",
                    "1 day",
                    "2 days",
                    "3 days",
                    "1 week"
            };

            builder.setTitle("Choose reminder");
            builder.setItems(reminderList, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    LocalDateTime tmpDateTime = LocalDateTime.of(eventDate, eventTime);

                    switch(which) {
                        case 0:
                            remindTime = null;
                            inputReminder.setText(reminderList[0]);
                            break;

                        case 1:
                            remindTime = tmpDateTime.minusMinutes(5);
                            inputReminder.setText(reminderList[1]);
                            break;

                        case 2:
                            remindTime = tmpDateTime.minusMinutes(10);
                            inputReminder.setText(reminderList[2]);
                            break;

                        case 3:
                            remindTime = tmpDateTime.minusMinutes(15);
                            inputReminder.setText(reminderList[3]);
                            break;

                        case 4:
                            remindTime = tmpDateTime.minusMinutes(30);
                            inputReminder.setText(reminderList[4]);
                            break;

                        case 5:
                            remindTime = tmpDateTime.minusHours(1);
                            inputReminder.setText(reminderList[5]);
                            break;

                        case 6:
                            remindTime = tmpDateTime.minusHours(2);
                            inputReminder.setText(reminderList[6]);
                            break;

                        case 7:
                            remindTime = tmpDateTime.minusHours(3);
                            inputReminder.setText(reminderList[7]);
                            break;

                        case 8:
                            remindTime = tmpDateTime.minusDays(1);
                            inputReminder.setText(reminderList[8]);
                            break;

                        case 9:
                            remindTime = tmpDateTime.minusDays(2);
                            inputReminder.setText(reminderList[9]);
                            break;

                        case 10:
                            remindTime = tmpDateTime.minusDays(3);
                            inputReminder.setText(reminderList[10]);
                            break;

                        case 11:
                            remindTime = tmpDateTime.minusWeeks(1);
                            inputReminder.setText(reminderList[11]);
                            break;
                    }

                }
            });

        }

        return builder.create();
    }

    /**
     * This method builds the event from given values,
     * saves it to the database and returns to previous activity
     */
    public void saveEvent() {
        Event tmpEvent = new Event();
        tmpEvent.setName(inputName.getText().toString());
        tmpEvent.setDescription(inputDesc.getText().toString());
        tmpEvent.setDate(eventDate);
        tmpEvent.setTime(eventTime);
        tmpEvent.setEventType(eventType);

        if(remindTime != null) {
            Debug.printConsole(TAG, "saveEvent", "Event: " + tmpEvent.getName(), 2);
            MyReminder reminder = new MyReminder(tmpEvent, remindTime.getHour(), remindTime.getMinute(), remindTime.getDayOfYear());
            createReminder(reminder);
        }

        if(currentMode == Mode.CREATE) {
            eventRepository.save(tmpEvent);
        } else if(currentMode == Mode.EDIT) {
            tmpEvent.setId(eventObject.getId());
            eventRepository.update(tmpEvent);
        }

        goBack(true);
    }

    /**
     * This returns result for the activity that started this activity
     *
     * @param status Tells if event was created/modified or not
     */
    private void goBack(boolean status) {
        Intent i = new Intent();
        i.putExtra("status", status);
        setResult(RESULT_OK, i);
        finish();
    }

    /**
     * This button goes back to the previous activity if back button is pressed
     */
    @Override
    public void onBackPressed() {
        goBack(false);
        super.onBackPressed();
    }

    /**
     * This sets reminder for the event
     *
     * @param reminder MyReminder Object which contains all needed values to create reminder
     */
    private void createReminder(MyReminder reminder) {

        int notificationId = 0;

        if(reminder.getEvent().getNotificationId() > 0) {
            notificationId = reminder.getEvent().getNotificationId();
        } else {
            notificationId = notificationCounter++;
            reminder.getEvent().setNotificationId(notificationId);
        }

        Intent notifyIntent = new Intent(this, MyReminderReceiver.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("event", reminder.getEvent());
        notifyIntent.putExtra("KEY_TODO", bundle);
        notifyIntent.putExtra("KEY_ID", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_REQ_CODE + notificationId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, reminder.getMinute());
        calendar.set(Calendar.HOUR_OF_DAY, reminder.getHour());
        calendar.set(Calendar.DAY_OF_YEAR, reminder.getDayOfTheYear());

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
