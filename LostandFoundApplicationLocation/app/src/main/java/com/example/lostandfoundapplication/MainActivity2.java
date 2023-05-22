package com.example.lostandfoundapplication;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    // Initialising location variables
    LocationManager locationManager;
    LocationListener locationListener;

    // initialising EditText variables and String to store location data
    EditText nameEditText;
    EditText phoneNumberEditText;
    EditText descriptionEditText;
    EditText dateEditText;
    String userLocation = "";

    // Checks to see if user has granted permission, if so, requests location updates from location listener.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Creating a new instance of DBHelper class, to open and manage SQLite database.
        DBHelper dbHelper = new DBHelper(this);

        // initialising radio buttons for lost / found selection.
        RadioButton radioButtonLost = findViewById(R.id.radioButtonLost);
        RadioButton radioButtonFound = findViewById(R.id.radioButtonFound);

        // Creating onClickListener's for radio buttons (and setting them to false) and save button post button.
        radioButtonLost.setOnClickListener(v -> radioButtonFound.setChecked(false));
        radioButtonFound.setOnClickListener(v -> radioButtonLost.setChecked(false));

        // Gets the location permission, and then listen for updates
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        // Creating onClickListener for locationButton, with initialisation.
        Button locationButton = findViewById(R.id.getLocationButton);
        locationButton.setOnClickListener(v -> {

            // Get the users current location.
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // If the location has no value, then user has not given their permission to access location data.
            if (location == null) {
                // Submit another request to user to access permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                // Get the latitude and longitude coordinates.
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                try {
                    // Geocoder class used to find the location name from maps coordinates.
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0)
                    {
                        userLocation = addresses.get(0).getLocality();
                    }

                } catch (IOException e) {
                    // Catch exception which handles the exception of location not found.
                    Toast.makeText(this, "Sorry, could not find your location", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Initialising save button and save button OnClickListener.
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {

            // if...else statement to set the status of radio buttons depending on which is selected.
            String lostOrFound = "";
            if (radioButtonLost.isChecked()) {
                lostOrFound = "Lost";
            } else if (radioButtonFound.isChecked()) {
                lostOrFound = "Found";
            }

            // Linking user input fields to code, to later get user input.
            nameEditText = findViewById(R.id.name);
            phoneNumberEditText = findViewById(R.id.phoneNumber);
            descriptionEditText = findViewById(R.id.itemDescription);
            dateEditText = findViewById(R.id.dateLostFound);

            // Storing the user input fields as string variables.
            String name = nameEditText.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String date = dateEditText.getText().toString();

            // Adding the user input to the SQLite database.
            dbHelper.addData(name, phoneNumber, description, date, userLocation, lostOrFound);

            // Intent takes the user back to the homepage after pressing the save button and other operations are complete.
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });


    }

}