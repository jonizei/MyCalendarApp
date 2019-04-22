package fi.tuni.mycalendarapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * This class is used for creating and updating database
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    /**
     * Classe's tag for debugging
     */
    private final static String TAG = "MyDatabaseHelper";

    /**
     * Constructor for MyDatabaseHelper
     *
     * @param context Application context
     * @param name Database name
     * @param factory CursorFactory object
     * @param version Database version
     */
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Initializes database
     *
     * @param db SQLiteDatabase object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        Debug.printConsole(TAG, "onCreate", "onCreate called", 1);

        try {
            db.execSQL(EventDatabaseAdapter.DATABASE_CREATE);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Upgrades database
     *
     * @param db SQLiteDatabase object
     * @param oldVersion Old version of the database
     * @param newVersion New version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Debug.printConsole(TAG, "onUpgrade", "Upgrading from version " + oldVersion + " to " + newVersion + " which will destroy all old data", 1);

        db.execSQL(EventDatabaseAdapter.DATABASE_REMOVE);
        onCreate(db);
    }
}
