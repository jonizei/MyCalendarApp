package fi.tuni.mycalendarapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = "MyDatabaseHelper";

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Debug.printConsole(TAG, "onCreate", "onCreate called", 1);

        try {
            db.execSQL(EventDatabaseAdapter.DATABASE_CREATE);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Debug.printConsole(TAG, "onUpgrade", "Upgrading from version " + oldVersion + " to " + newVersion + " which will destroy all old data", 1);

        db.execSQL(EventDatabaseAdapter.DATABASE_REMOVE);
        onCreate(db);
    }
}
