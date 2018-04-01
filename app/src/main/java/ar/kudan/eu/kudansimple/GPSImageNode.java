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
 * GPSNode class uses degress for bearing and meters for device height.
 */

public class GPSImageNode extends ARImageNode {

    private Location gpsLocation; // Location of the object

    private float bearing; // In degrees
    private float deviceHeight; // Height of the deviced

    //Speed and direction of the movement. ( Between last two locations gathered from GPS )
    private float speed;
    private float direction;

    private long previousFrameTime; //Testing

    /**
     * Initializes a new GPSNode
     * @param photo Picture that will be shown on the map.
     * @param location Location of the Node.
     * @param bearing Rotation of the photo. 0 Means picture is facing to East.
     */
    public GPSImageNode(String photo, Location location, float bearing) {
        super(photo);

        this.deviceHeight = 1.5f;
        this.previousFrameTime = 0;

        this.setGpsLocation(location, bearing);
    }

    /**
     * Initializes a new GPSNode facing to East.
     * @param photo Picture that will be shown on the map.
     * @param l Location of the Node.
    */
    public GPSImageNode(String photo, Location l) {
        this(photo, l, 0);
    }

    /**
     * Get location of the ImageNode.
     * @return Location of the node
     */
    public Location getGpsLocation() {

        return this.gpsLocation;
    }

    /**
     * Sets location of the ImageNode.
     * @param l Desired location.
     * @param bearing Rotation from East.
     */
    public void setGpsLocation(Location l, float bearing) {
        if (l != null)
            this.gpsLocation = l;
        this.bearing = bearing;
    }

    /**
     * Sets location of the ImageNode.
     * @param l Desired location.
     */
    public void setGpsLocation(Location l) {
        this.gpsLocation = l;
    }

    /**
     * Sets bearing of the object. 0 bearing means object is looking to East, 90 means South, 180 means West, 270 or -90 means North.
     * @param bearing
     */
    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    /**
     * Calculates translation vector with given angle and distance from node A to node B.
     * A and B are not parameters for this function.
     * @param bearing Bearing of the node from destination point to target point in degrees.
     * @param distance Distance between two nodes.
     * @return Translation vector. If this vector is applied to node A, It's new position will be the same as node B's position.
     */
    public Vector3f calculateTranslationVector(float bearing, float distance) {

        Vector3f northVector = new Vector3f(-1, 0, 0); //Default north vector.

        Quaternion q = new Quaternion();
        northVector = q.fromAngleAxis((float) Math.toRadians(bearing), new Vector3f(0, -1, 0)).mult(northVector); //North vector is rotated by angle between Node A and Node B's position vectors.

        Log.d("NODE_DEBUG", "Rotatedvec : " + northVector);

        northVector = northVector.mult(distance); //Vector is multiplied by distance because it was a unit vector.

        return northVector;
    }

    /**
     * Updates the ARWorld position of the ImageNode.
     * @param l Current location of the device.
     */
    public void updateWorldPosition(Location l) {

        Location currentLocation = l;

        Log.d("NODE_DEBUG", "This Location : " +  gpsLocation);
        Log.d("NODE_DEBUG", "Current Location : " + currentLocation);

        float distanceToObject = gpsLocation.distanceTo(currentLocation); //In meters
        float bearingToObject = GPSManager.bearingFrom(gpsLocation, currentLocation); //In degrees

        Log.d("NODE_DEBUG", "Bearing : " + bearingToObject);
        Log.d("NODE_DEBUG", "Distance : " + distanceToObject);

        this.speed = currentLocation.getSpeed(); //TODO: Not working.
        this.direction = currentLocation.getBearing(); //TODO: Not Working.

        Vector3f translationVector = this.calculateTranslationVector(bearingToObject, distanceToObject); //For updating the position of the node at the new location of the user.

        translationVector.y = -deviceHeight; //Set default node height as device's height.

        this.setPosition(translationVector); //Change pooition to new position.

        Quaternion qt = new Quaternion();
        this.setOrientation(qt.fromAngleAxis((float) Math.toRadians(bearing), new Vector3f(0, -1, 0))); //Rotate Image according to it's bearing.

        Log.d("NODE_DEBUG", this.getPosition().toString());
    }

    /**
     * For smoothing movement. Currently in testing stage.
     */
    @Override
    public void preRender() {
        if(GPSManager.interpolateMotionUsingHeading) {
            Log.d("NODE_DEBUG", this.speed + " : " +  this.direction);
            if (this.speed > 0 && this.direction > 0) {
                long currentTime = ARRenderer.getInstance().getRenderTime();
                long timeDelta = currentTime - this.previousFrameTime;

                Log.d("NODE_DEBUG", "preRender: " + currentTime + " : " + this.previousFrameTime);
                if (timeDelta > 1) {
                    Log.d("NODE_DEBUG", "preRender: INTERPOLATE");
                    Vector3f translationVector = this.calculateTranslationVector(this.direction, timeDelta * this.speed);
                    this.translateByVector(translationVector.negate());
                }

                this.previousFrameTime = currentTime;
            }
        }

        super.preRender();
    }
}