package com.example.lostandfoundapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    // Initialising the strings to be displayed to the user with information gathered from the database.
    private String lostOrFound;
    private String name;
    private String phoneNumber;
    private String itemDescription;
    private String dateLostFound;
    private String locationLostFound;

    // onCreate method used to access the database instance.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Grab the intent from the previous screen.
        Intent intent = getIntent();
        lostOrFound = intent.getStringExtra("lostOrFound");
        name = intent.getStringExtra("name");
        phoneNumber = intent.getStringExtra("phoneNumber");
        itemDescription = intent.getStringExtra("itemDescription");
        dateLostFound = intent.getStringExtra("dateLostFound");
        locationLostFound = intent.getStringExtra("locationLostFound");

        // Sets the values of the text views to the appropriate columns.
        TextView lostFoundTextView = findViewById(R.id.lostFound);
        TextView nameTextView = findViewById(R.id.name);
        TextView phoneNumberTextView = findViewById(R.id.phoneNumber);
        TextView itemDescriptionTextView = findViewById(R.id.itemDescription);
        TextView dateTextView = findViewById(R.id.dateLostFound);
        TextView itemLocationTextView = findViewById(R.id.locationLostFound);

        // Displays the text of the opened post to the user.
        lostFoundTextView.setText(lostOrFound);
        nameTextView.setText("Posted by: \n" + name);
        phoneNumberTextView.setText("Contact them: \n" + phoneNumber);
        itemDescriptionTextView.setText("Item description: \n" + itemDescription);
        dateTextView.setText("Date found or lost: \n" + dateLostFound);
        itemLocationTextView.setText("Item found or lost at: \n" +locationLostFound);

        // initialising the deletePostButton and onClickListener for delete post button.
        Button deletePostButton = findViewById(R.id.deletePostButton);
        deletePostButton.setOnClickListener(v -> deletePost());
    }

    // deletePost method. When the user selects the delete post button, the database is accessed and told to remove all listed columns from the database.
    private void deletePost() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection =
                DBHelper.COLUMN_LOST_FOUND + " = ? AND " +
                DBHelper.COLUMN_NAME + " = ? AND " +
                DBHelper.COLUMN_PHONE_NUMBER + " = ? AND " +
                DBHelper.COLUMN_ITEM_DESCRIPTION + " = ? AND " +
                DBHelper.COLUMN_DATE + " = ? AND " +
                DBHelper.COLUMN_ITEM_LOCATION + " = ?";

        String[] selectionArgs = {lostOrFound, name, phoneNumber, itemDescription, dateLostFound, locationLostFound};
        db.delete(DBHelper.TABLE_NAME, selection, selectionArgs);
        db.close();

        finish();
    }
}