package ar.kudan.eu.kudansimple.GPSUtils.LocationUtils;


import android.app.Activity;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class PlayLocationManager {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private final int INTERVAL = 10000; /* 10 seconds */
    private final int FASTEST_INTERVAL = 5000; /* 5 seconds */

    private Activity activity;
    private PlayLocationListener listener;

    /**
     * Constructor for {@link PlayLocationManager}
     * @param activity Current activity
     * @param listener Location listener for parsing updates.
     */
    public PlayLocationManager(Activity activity, PlayLocationListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    /**
     * Start method for starting the location updates.
     */
    public void start() {
        if (createClient())
            startLocationUpdates();
    }

    /**
     * Checks if googlePlayAPI is available or not
     * @return is it available ?
     */
    private boolean googlePlayAPIAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity.getApplicationContext()) == ConnectionResult.SUCCESS;
    }

    /**
     * Creates the client for location updates
     * @return creation successful.
     */
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
                
            };
        } else {
            return false;
        }

        return true;
    }

    /**
     * Starts location updates
     * @throws SecurityException exception
     */
    private void startLocationUpdates() throws  SecurityException {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback,
            null);
    }

}
