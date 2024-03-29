package fi.tuni.mycalendarapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * This class handles notifications when its time to remind
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class MyReminderService extends JobIntentService {

    /**
     * Classe's tag for debugging
     */
    private final static String TAG = "MyReminderService";

    /**
     * Notification channel name
     */
    private final static String CHANNEL_NAME = "ScEventChannel";

    /**
     * Notification channel id
     */
    private final static String CHANNEL_ID = "sc_event_channel";

    /**
     * Notification request code
     */
    private final static int NOTIFICATION_REQ_CODE = 10;

    /**
     * Constructor for MyReminderService
     */
    public MyReminderService() {
        Debug.printConsole(TAG, "Constructor", "Created", 1);
    }

    /**
     * This method handles showing the notifications
     *
     * @param intent Intent which contains data about event
     */
    @Override
    protected void onHandleWork(@Nullable Intent intent) {

        Debug.printConsole(TAG, "onHandleWork", "onHandleWork called", 1);

        Bundle extras = intent.getExtras();

        if(extras != null) {
            Event event = extras.getParcelable("event");
            int notificationId = extras.getInt("id");

            if (event != null) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                builder.setContentTitle(event.getName());
                builder.setContentText(event.getDate() + " " + event.getTime() + " " + event.getDescription());
                builder.setSmallIcon(R.mipmap.ic_launcher);

                Intent notifyIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_REQ_CODE + notificationId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(pendingIntent);

                Notification notification = builder.build();
                NotificationManagerCompat manager = NotificationManagerCompat.from(this);
                manager.notify(notificationId, notification);

            }

        }

    }

}
