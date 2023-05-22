package com.example.lostandfoundapplication;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostandfoundapplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Default maps activity code.
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Creating an array of LatLng objects which stores the coordinates of the map markers.
        LatLng[] locations = new LatLng[] {
                new LatLng(-37.849770, 145.109064),
                new LatLng(-38.149938, 144.360184),
                new LatLng(-37.815228, 144.964599)
        };

        // Create another array for the MarkerOptions objects to define marker properties.
        MarkerOptions[] options = new MarkerOptions[] {
                new MarkerOptions().position(locations[0]).title("Burwood Map Marker"),
                new MarkerOptions().position(locations[1]).title("Geelong Map Marker"),
                new MarkerOptions().position(locations[2]).title("Melbourne Map Marker")
        };

        // Creating a bounds object to specify the bounds of the area that the camera will focus on.
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(locations[0])
                .include(locations[1])
                .include(locations[2])
                .build();

        // A for loop used to iterate over the array and add associated marker to Google map.
        for (int i = 0; i < locations.length; i++) {
            mMap.addMarker(options[i]);
        }

        // Position the camera over the bounded area of location markers.
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

    }
}