package com.example.lostandfoundapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity3 extends AppCompatActivity {

    // Initialising SimpleCursorAdapter to bind data from SQLite database to ListView.
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Finds the ListView, enabling user to see list of posts. Utilises SimpleCursorAdapter to bind data from SQLite database.
        // Utilised to display lost and found status and item description to user when viewing list of all posts.
        ListView listView = findViewById(R.id.list_view);
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.lost_found_item,
                null,
                new String[] {DBHelper.COLUMN_LOST_FOUND, DBHelper.COLUMN_ITEM_DESCRIPTION},
                new int[] {R.id.lost_found, R.id.item_description},
                0);

        // Setting the adapter to display data from the SQLite database.
        listView.setAdapter(adapter);

        // Setting up onClickListener for when user selects a post from the view all screen.
        // Gets the cursor position and appropriate database entries.
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) adapter.getItem(position);
            String lostOrFound = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOST_FOUND));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PHONE_NUMBER));
            String itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ITEM_DESCRIPTION));
            String dateLostFound = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE));
            String locationLostFound = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ITEM_LOCATION));

            // Intent which moves the user to the view selected post screen and forwards appropriate database entries.
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            intent.putExtra("lostOrFound", lostOrFound);
            intent.putExtra("name", name);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("itemDescription", itemDescription);
            intent.putExtra("dateLostFound", dateLostFound);
            intent.putExtra("locationLostFound", locationLostFound);
            startActivity(intent);
        });

        loadData();
    }


    // onResume method used to load in data from the SQLite database when screen is viewed by the user.
    // Ensures that the activity screen is properly resumed and displaying correct data to user.
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    // loadData method is utilised to access database, fetch the appropriate columns and display them to the user.
    private void loadData() {
        DBHelper mDbHelper = new DBHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_LOST_FOUND,
                DBHelper.COLUMN_NAME,
                DBHelper.COLUMN_PHONE_NUMBER,
                DBHelper.COLUMN_ITEM_DESCRIPTION,
                DBHelper.COLUMN_DATE,
                DBHelper.COLUMN_ITEM_LOCATION
        };

        // Specifies the order in which to display database entries, which in this case is descending from date of entry.
        String sortOrder = DBHelper.COLUMN_DATE + " DESC";

        // Fetches the database cursor containing the data and the appropriate table, and specifies the array of strings to display.
        Cursor cursor = db.query(
                DBHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        // Ensures the ListView can display the database entries.
        adapter.swapCursor(cursor);
    }
}