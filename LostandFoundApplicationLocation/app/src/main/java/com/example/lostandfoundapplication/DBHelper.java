package com.example.lostandfoundapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Initialising DBHelper with new instance of SQLiteOpenHelper class.
public class DBHelper extends SQLiteOpenHelper {

    // Initialising database name, version, table and column names.
    public static final String DATABASE_NAME = "lostFoundDatabase.db";
    public static final int DATABASE_VERSION = 6; // Version 6 selected due to various revisions made to database through my development of the app.
    public static final String TABLE_NAME = "lostFoundTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOST_FOUND = "lost_found";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_ITEM_DESCRIPTION = "item_description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ITEM_LOCATION = "item_location";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    // Database constructor, used to create, open and add or remove data when requested by the user.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate method used to create the database. Creates table and adds the listed columns. to the database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LOST_FOUND + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_ITEM_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_ITEM_LOCATION + " TEXT,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL" +")";
        db.execSQL(CREATE_TABLE);
    }

    // onUpgrade method used to upgrade database if an older version is discovered.
    // In this iteration this was required as I added the LOST_FOUND column in after establishing a previous schema.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 6) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_LATITUDE + " REAL");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_LONGITUDE + " REAL");
        }
    }

    // addData method is utilised to add new data to the database based on the specified input methods. Closes the database once completed.
    public void addData(String name, String phoneNumber, String itemDescription,
                        String date, String itemLocation, String lostOrFound, Double latitude, Double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOST_FOUND, lostOrFound);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_ITEM_DESCRIPTION, itemDescription);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_ITEM_LOCATION, itemLocation);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

}
