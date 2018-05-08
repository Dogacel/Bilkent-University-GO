package ar.kudan.eu.kudansimple.gps.location;


import android.app.Activity;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayLocationManager {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private int intervalMax = 4000; /* 10 seconds */
    private int intervalFastest = 2000; /* 5 seconds */

    private Activity activity;
    private ArrayList<PlayLocationListener> listeners;

    /**
     * Constructor for {@link PlayLocationManager}
     * @param activity Current activity
     * @param listeners Location listeners for parsing updates.
     */
    public PlayLocationManager(Activity activity, PlayLocationListener... listeners) {
        this.activity = activity;
        this.listeners = new ArrayList<>(listeners.length);

        this.listeners.addAll(Arrays.asList(listeners));
    }


    /**
     * Sets the desired intervals.
     * @param intervalMax Max interval between location requests.
     * @param intervalFastest Fastest interval between two location requests.
     */
    public void setIntervals(int intervalMax, int intervalFastest) {
        this.intervalFastest = intervalFastest;
        this.intervalMax = intervalMax;
    }

    /**
     * Adds a listener to the listeners list
     * @param listener Listener to be added.
     */
    public void addListener(PlayLocationListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Start method for starting the location updates.
     */
    public void start() {
        if (createClient())
            startLocationUpdates();
    }

    /**
     * Stop the client.
     */
    public void stop() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
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
            mLocationRequest.setInterval(intervalMax);
            mLocationRequest.setFastestInterval(intervalFastest);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        for (PlayLocationListener listener : listeners) {
                            listener.parseUpdate(location);
                        }
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
