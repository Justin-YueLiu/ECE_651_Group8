package com.ece651group8.uwaterloo.ca.ece_651_group8.util;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.ece651group8.uwaterloo.ca.ece_651_group8.PatientActivity;

/**
 * Created by liuyue on 2016-11-11.
 */

public class LocationEventListener implements LocationListener {

    private Context locationContext;
    private String msg;

    public LocationEventListener(Context context, String locationMsg){
        locationContext = context;
        msg = locationMsg;
    }

    @Override
    public void onLocationChanged(Location location) {

        msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        Log.i("bbbbbbbbbbbbb",msg);
        Toast.makeText(locationContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        locationContext.startActivity(intent);
        Toast.makeText(locationContext, "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(locationContext, "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}


