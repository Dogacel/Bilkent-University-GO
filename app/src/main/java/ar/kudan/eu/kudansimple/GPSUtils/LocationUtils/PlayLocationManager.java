package ar.kudan.eu.kudansimple.GPSUtils.LocationUtils;


import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class PlayLocationManager {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private final int INTERVAL = 10000; /* 10 seconds */
    private final int FASTEST_INTERVAL = 5000; /* 5 seconds */

    private Activity activity;
    private PlayLocationListener listener;

    public PlayLocationManager(Activity activity, PlayLocationListener listener) {
        this.activity = activity;
        this.listener = listener;

        if (createClient())
            startLocationUpdates();
    }

    private boolean googlePlayAPIAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity.getApplicationContext()) == ConnectionResult.SUCCESS;
    }

    private boolean createClient() {
        if (googlePlayAPIAvailable()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        listener.parseUpdate(location);
                    }
                }

                ;
            };
        } else {
            return false;
        }

        return true;
    }


    private void startLocationUpdates() throws  SecurityException {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback,
            null);
    }

}
