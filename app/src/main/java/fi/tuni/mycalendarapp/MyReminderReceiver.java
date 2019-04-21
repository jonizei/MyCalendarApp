package fi.tuni.mycalendarapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;

public class MyReminderReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReminderReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Debug.printConsole(TAG, "onReceive", "onReceiveCalled", 1);

        Bundle extras = intent.getExtras();

        if(extras != null) {

            Bundle bundle = extras.getBundle("KEY_TODO");

            if(bundle != null) {
                Event event = bundle.getParcelable("event");
                int notificationId = extras.getInt("KEY_ID");
                Debug.printConsole(TAG, "onReceive", "Event: " + event.getName(), 3);
                Intent i = new Intent();
                i.putExtra("event", event);
                i.putExtra("id", notificationId);
                JobIntentService.enqueueWork(context, MyReminderService.class, 1, i);
            }

        }

    }

}
