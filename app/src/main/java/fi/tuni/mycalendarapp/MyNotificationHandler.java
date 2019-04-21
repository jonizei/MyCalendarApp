package fi.tuni.mycalendarapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class MyNotificationHandler {

    private static Context context;

    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;

    private static int notificationCounter = 1;
    private static MyNotificationHandler instance;

    private final static String CHANNEL_NAME = "EventNotifications";
    private final static int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private final static String CHANNEL_ID = "EventChannel";

    private MyNotificationHandler() {
        notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
        notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public static void setContext(Context ctx) {
        context = ctx;
    }

    public static MyNotificationHandler getInstance() {

        if(instance == null) {
            instance = new MyNotificationHandler();
        }

        return instance;
    }

    public void displayNotification(String title, String text) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);

        Notification notification = mBuilder.build();
        int mId = notificationCounter++;

        notificationManager.notify(mId, notification);
    }

}
