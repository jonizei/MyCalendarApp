package fi.tuni.mycalendarapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;

/**
 * This class listens when its time to show reminder notification
 *
 * @author Joni Koskinen
 * @version 2019-04-23
 */
public class MyReminderReceiver extends BroadcastReceiver {

    /**
     * Classe's tag used for debugging
     */
    private static final String TAG = "MyReminderReceiver";

    /**
     * This triggers when receives a broadcast then it starts MyReminderService
     *
     * @param context Application context
     * @param intent Intent that contains data
     */
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
