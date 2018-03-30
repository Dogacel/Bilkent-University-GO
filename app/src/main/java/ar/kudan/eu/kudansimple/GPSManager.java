package ar.kudan.eu.kudansimple;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import eu.kudan.kudan.ARGyroManager;
import eu.kudan.kudan.ARNode;
import eu.kudan.kudan.ARRenderer;
import eu.kudan.kudan.ARRendererListener;
import eu.kudan.kudan.ARWorld;

/**
 * Created by dogacel on 21.03.2018.
 */

public class GPSManager implements LocationListener, ARRendererListener{

    private static GPSManager gpsManager;

    private ARWorld arWorld;
    private Activity activity;

    private Location previousLocation;
    private LocationManager locationManager;

    public String provider;


    public static boolean interpolateMotionUsingHeading;

    /**
     * Consturctor for GPSManager
     * @param world ARWorld for displaying GPSNode objects.
     * @param activity Current activity for getting location service.
    */
    public GPSManager (ARWorld world, Activity activity) {


        this.arWorld = world;
        this.activity = activity;

        this.locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        // set preferred provider based on the best accuracy possible
        Criteria fineAccuracyCriteria = new Criteria();
        fineAccuracyCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(fineAccuracyCriteria, true);

        this.previousLocation = null;

        ARGyroManager gyroManager = ARGyroManager.getInstance();
        gyroManager.initialise();

        this.arWorld.setVisible(false);
        interpolateMotionUsingHeading = false;
    }


    /**
     * Starts the GPSManager
     */
    public void start() {

        this.startLocationUpdates();

        ARGyroManager.getInstance().start();

        ARRenderer.getInstance().addListener(this);
    }



    public Location getCurrentLocation() {return previousLocation;}

    /**
     * Gets location for determining relative positions of objects.
     * @return last location got from GPS_PROVIDER
     */
    private Location startLocationUpdates() {

        Location location = null;


        this.arWorld.setVisible(true);

        try {

            locationManager.requestLocationUpdates(
                    provider,
                    0,
                    0, this);

            Log.d("GPS Enabled", "GPS Enabled");
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Returns current ARWorld.
     * @return current ARWorld.
     */
    public ARWorld getArWorld() {
        return this.arWorld;
    }


    /**
     * Sets current ARWorld to world
     * @param world
     * @return success.
     */
    public boolean setArWorld(ARWorld world) {
        if (world == null) {
            return false;
        }
        this.arWorld = world;
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            this.previousLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (getCurrentLocation() != null) {
                for (ARNode node : this.getArWorld().getChildren()) {
                    if (node instanceof GPSNode) {
                        GPSNode gnode = (GPSNode) node;
                        gnode.updateWorldPosition(previousLocation);
                    }
                }
                Log.d("GPS", previousLocation.toString());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        //TODO: update each node's position on ARWorld.


        //foreach child in this.arWorld.children : update location.
    }


    public static float bearingFrom(Location source, Location destination) {
        return source.bearingTo(destination);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void updateNode() {
        this.arWorld.setOrientation(ARGyroManager.getInstance().getWorld().getOrientation());
    }

    @Override
    public void preRender() {
        this.updateNode();
    }

    @Override
    public void postRender() {}

    @Override
    public void rendererDidPause() {}

    @Override
    public void rendererDidResume() {}
}
