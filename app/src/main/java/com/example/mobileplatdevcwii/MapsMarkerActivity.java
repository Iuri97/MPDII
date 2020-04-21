// Iuri Insali S1504620

package com.example.mobileplatdevcwii;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    Roadwork theItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent theIntent = getIntent();
        theItem = (Roadwork)theIntent.getSerializableExtra("Roadwork");
        System.out.println((Roadwork)theIntent.getSerializableExtra("Roadwork"));



        // Retrieve the content view that renders the map.
        setContentView(R.layout.maps_view);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        String latitude = theItem.getLat();
        String longitude = theItem.getLon();
        double Lat = Double.valueOf(latitude);
        double Long = Double.valueOf(longitude);


        LatLng location = new LatLng(Lat,Long);
        Marker m = googleMap.addMarker(new MarkerOptions().position(location)
                        .title(theItem.getTitle())
                        .snippet(theItem.getDescription()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));



    }
}
