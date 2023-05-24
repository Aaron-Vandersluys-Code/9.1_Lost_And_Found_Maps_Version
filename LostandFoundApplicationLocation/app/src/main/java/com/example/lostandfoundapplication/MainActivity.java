package com.example.lostandfoundapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OnClickListener for newPost button, taking user to the new post screen.
        // Includes button initialisation.
        Button newPost = findViewById(R.id.createNewButton);
        newPost.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        // OnClickListener for showAll button, taking user to the view all posts screen.
        // Includes button initialisation.
        Button showAll = findViewById(R.id.showAllButton);
        showAll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(intent);
        });

        // OnClickListener for showMap button, taking user to the view all on map screen.
        // Includes button initialisation.
        Button showMap = findViewById(R.id.showMapButton);
        showMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class); // update once map page built
            startActivity(intent);
        });

    }
}

