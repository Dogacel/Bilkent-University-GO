package ar.kudan.eu.kudansimple;

import android.location.Location;
import android.util.Log;

import com.google.vrtoolkit.cardboard.sensors.internal.Vector3d;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import eu.kudan.kudan.ARNode;
import eu.kudan.kudan.ARRenderer;

/**
 * Created by dogacel on 21.03.2018.
 */

public class GPSNode extends ARNode {

    private Location gpsLocation;
    private Vector3f position;
    private Quaternion orientation;

    private float bearing;
    private float deviceHeight;

    private float speed;
    private float direction;

    private long previousFrameTime;

    private boolean interpolateMotionUsingHeading;

    public GPSNode(Location location, float bearing) {
        //GPSManager *gpsManager = [GPSManager getInstance];
        deviceHeight = 1.5f;
        interpolateMotionUsingHeading = false;
        previousFrameTime = 0;

        if (true)  // [gpsManager getCurrentLocation] != nil
        {
            this.setGpsLocation(location, bearing);
        }
    }

    public GPSNode(Location l) {
        this(l, 0);
    }

    public Location getGpsLocation() {

        return this.gpsLocation;
    }

    public void setGpsLocation(Location l, float bearing) {
        this.gpsLocation = l;
        this.bearing = bearing;
    }

    public void setGpsLocation(Location l) {
        this.gpsLocation = l;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public Vector3f calculateTranslationVector(float bearing, float distance) {
        Vector3f northVector = new Vector3f(-1, 0, 0);

        Quaternion q = new Quaternion();
        northVector = q.fromAngleAxis((float) bearing, new Vector3f(0, -1, 0)).mult(northVector);

        northVector.mult(distance);

        return northVector;
    }

    public void updateWorldPosition() {
        Location currentLocation = GPSManager.getInstance().getCurrentLocation();

        float distanceToObject = gpsLocation.distanceTo(currentLocation);
        float bearingToObbject = GPSManager.getInstance().bearingFrom(gpsLocation, currentLocation);

        this.speed = currentLocation.getSpeed();
        this.direction = currentLocation.getBearing();

        Vector3f translationVector = this.calculateTranslationVector(bearingToObbject, distanceToObject);

        translationVector.y = -deviceHeight;

        this.position = translationVector;

        Quaternion qt = new Quaternion();
        this.orientation = qt.fromAngleAxis(this.bearing, new Vector3f(0, -1, 0));
    }

    @Override
    public void preRender() {
        if(this.interpolateMotionUsingHeading) {
            if (this.speed > 0 && this.direction > 0) {
                long currentTime = ARRenderer.getInstance().getRenderTime();
                long timeDelta = currentTime - this.previousFrameTime;

                if (timeDelta < 1) {
                    Vector3f translationVector = this.calculateTranslationVector(this.direction, timeDelta * this.speed);
                    this.translateByVector(translationVector.negate());
                }

                this.previousFrameTime = currentTime;
            }
        }

        super.preRender();
    }
}