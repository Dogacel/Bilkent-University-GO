package ar.kudan.eu.kudansimple;

import android.location.Location;
import android.util.Log;

import com.google.vrtoolkit.cardboard.sensors.internal.Vector3d;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARNode;
import eu.kudan.kudan.ARRenderer;

/**
 * Created by dogacel on 21.03.2018.
 */

public class GPSNode extends ARImageNode {

    private Location gpsLocation;

    private float bearing; // In degrees
    private float deviceHeight;

    private float speed;
    private float direction;

    private long previousFrameTime;

    private Location northPole;
    private Vector3f northVector;

    public GPSNode(String photo, Location location, float bearing) {
        super(photo);

        deviceHeight = 1.5f;
        previousFrameTime = 0;

        this.northVector = null;
        this.setGpsLocation(location, bearing);
    }

    public void setNorthPole(Location l) {
        this.northPole = l;
    }

    public void setNorthVector(Vector3f vector) {
        this.northVector = vector;
    }

    public GPSNode(String photo, Location l) {
        this(photo, l, 0);
    }

    public Location getGpsLocation() {

        return this.gpsLocation;
    }

    public void setGpsLocation(Location l, float bearing) {
        if (l != null)
            this.gpsLocation = l;
        this.bearing = bearing;
    }

    public void setGpsLocation(Location l) {
        this.gpsLocation = l;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public Vector3f calculateTranslationVector(float bearing, float distance, Location currentLocation) {

        if (northVector == null) {

        }

        Vector3f rotatedNorthVector = northVector;

        Quaternion q = new Quaternion();
        rotatedNorthVector = q.fromAngleAxis((float) Math.toRadians(bearing), new Vector3f(0, -1, 0)).mult(northVector);

        rotatedNorthVector.mult(distance);

        return rotatedNorthVector;
    }

    public void updateWorldPosition(Location l) {

        Location currentLocation = l;
        Log.d("GPS", gpsLocation.toString());

        float distanceToObject = gpsLocation.distanceTo(currentLocation);
        float bearingToObject = GPSManager.bearingFrom(gpsLocation, currentLocation);

        this.speed = currentLocation.getSpeed();
        this.direction = currentLocation.getBearing();

        Vector3f translationVector = this.calculateTranslationVector(bearingToObject, distanceToObject, currentLocation);

        translationVector.y = -deviceHeight / 100;

        this.setPosition(translationVector.mult(-100));

        Quaternion qt = new Quaternion();
        this.setOrientation(qt.fromAngleAxis((float) Math.toRadians(bearing), new Vector3f(0, -1, 0)));

        Log.d("POS", this.getPosition().toString());
        Log.d("POS", this.getOrientation().toString());
    }

    @Override
    public void preRender() {
        if(GPSManager.interpolateMotionUsingHeading) {
            Log.d("GPS", this.speed + " : " +  this.direction);
            if (this.speed > 0 && this.direction > 0) {
                long currentTime = ARRenderer.getInstance().getRenderTime();
                long timeDelta = currentTime - this.previousFrameTime;

                Log.d("GPS", "preRender: " + currentTime + " : " + this.previousFrameTime);
                if (timeDelta > 1) {
                    Log.d("GPS", "preRender: INTERPOLATE");
                    Vector3f translationVector = this.calculateTranslationVector(this.direction, timeDelta * this.speed, this.gpsLocation);
                    this.translateByVector(translationVector.negate());
                }

                this.previousFrameTime = currentTime;
            }
        }

        super.preRender();
    }
}