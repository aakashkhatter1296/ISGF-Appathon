package com.nsit.hack.energy.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.nsit.hack.energy.R;
import com.nsit.hack.energy.utils.SharedPrefs;

/**
 * Created by joydeep on 13/3/16.
 */
public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    double latitude = 0;
    double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng centerOfMap = googleMap.getCameraPosition().target;
                latitude = centerOfMap.latitude;
                longitude = centerOfMap.longitude;
                SharedPrefs.setPrefs("latitude", (float) latitude);
                SharedPrefs.setPrefs("longitude", (float) longitude);
                Log.d("location", latitude + " " + longitude);
            }
        });
        try {
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initilizeMap() {
        if (googleMap == null) {
            MapFragment mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
            mapFrag.getMapAsync(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        //final MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Home");
        //googleMap.addMarker(marker);
        /*googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {

                // Get the center of the Map.
                LatLng centerOfMap = googleMap.getCameraPosition().target;
                googleMap.clear();
                now = googleMap.addMarker(new MarkerOptions().position(centerOfMap));
                // Update your Marker's position to the center of the Map.
                now.setPosition(centerOfMap);
            }
        });*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
