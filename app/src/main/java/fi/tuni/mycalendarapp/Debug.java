package fi.tuni.mycalendarapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Debug {

    public static int DEBUG_LEVEL = 1;
    private static Context context;

    public static void print(String tag, String msg, int level) {
        if(BuildConfig.DEBUG) {
            if(level <= DEBUG_LEVEL) {
                Log.d(tag, msg);
            }
        }
    }

    public static void printConsole(String className, String methodName, String msg, int level) {
        if(BuildConfig.DEBUG) {
            if(level <= DEBUG_LEVEL) {
                String message = methodName + " " + msg;
                Log.d(className, message);
            }
        }
    }

    public static void printUI(String className, String methodName, String msg, int level) {
        if(BuildConfig.DEBUG) {
            if(level <= DEBUG_LEVEL) {
                String message = methodName + " " + msg;
                Toast.makeText(context, className + ": " + message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void loadDebug(Context host) {
        context = host;
        DEBUG_LEVEL = R.integer.debug_level;
    }

}