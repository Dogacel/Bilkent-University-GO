package ar.kudan.eu.kudansimple.gps.ar.units;

import android.location.Location;
import android.util.Log;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARRenderer;

/**
 * GPSNode class uses degrees for bearing and meters for device height.
 */

public class GPSImageNode extends ARImageNode {

    private float objectHeight; // Height of the device.
    public float getObjectHeight() { return objectHeight; }

    private Location gpsLocation; // Location of the object

    private float bearing; // In degrees
    public float getBearing() { return bearing; }

    private String photo;
    public String getPhoto() {return photo; }

    //Speed and direction of the movement. ( Between last two locations gathered from GPS )
    private float speed;
    private float direction;
    private float lastBearing;

    private boolean isStatic;
    public boolean isStatic() { return isStatic; }

    private boolean staticVisibility;

    private String ID;

    private long previousFrameTime; //Testing

    private float scale;
    @Override
    public void scaleByUniform(float scale) {
        super.scaleByUniform(scale);
        this.scale = scale;
    }
    public float getLastScale() {return scale; }

    /**
     * Initializes a new GPSNode
     *
     * @param id       ID of the node.
     * @param photo    Picture that will be shown on the map.
     * @param location Location of the Node.
     * @param height   Height of the object from the ground.
     * @param bearing  Rotation of the photo. 0 Means picture is facing to East.
     * @param isStatic Whether the image of the node is static or not.
     */
    public GPSImageNode(String id, String photo, Location location, float height, float bearing, boolean isStatic) {
        super(photo);

        this.photo = photo;

        this.lastBearing = 0;
        this.previousFrameTime = 0;
        this.isStatic = isStatic;
        this.objectHeight = -height;

        this.ID = id;
        this.staticVisibility = true;

        this.setGpsLocation(location, bearing);
    }

    /**
     * Initializes a new GPSNode facing to East.
     *
     * @param id     ID of the node.
     * @param photo  Picture that will be shown on the map.
     * @param l      Location of the Node.
     * @param height Height of the node from the ground.
     */
    public GPSImageNode(String id, String photo, Location l, float height) {
        this(id, photo, l, height, 0, true);
    }

    /**
     * Returns the ID of the node
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Get location of the ImageNode.
     *
     * @return Location of the node
     */
    public Location getGpsLocation() {

        return this.gpsLocation;
    }

    /**
     * Sets location of the ImageNode.
     *
     * @param l Desired location.
     */
    public void setGpsLocation(Location l) {
        this.gpsLocation = l;
    }

    /**
     * Sets location of the ImageNode.
     *
     * @param l       Desired location.
     * @param bearing Rotation from East.
     */
    private void setGpsLocation(Location l, float bearing) {
        if (l != null)
            this.gpsLocation = l;
        this.bearing = bearing + GPSManager.getRealBearing();
    }

    /**
     * Sets bearing of the object. 0 bearing means object is looking to East, 90 means South, 180 means West, 270 or -90 means North.
     *
     * @param bearing Rotation from East.
     */
    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    /**
     * Sets node to statically drawn.
     */
    public void setStatic() {
        this.isStatic = true;
    }

    /**
     * Sets node to dynamically drawn.
     */
    public void setDynamic() {
        this.isStatic = false;
    }

    /**
     * Changes the static visibility for seperating the visibility from AR classes and App classes.
     * @param visibility New visibility value
     */
    public void setStaticVisibility(boolean visibility) {
        this.staticVisibility = visibility;
    }

    /**
     * Returns the static state of the object's visibility.
     * @return static visibility
     */
    public boolean getStaticVisibility() {
        return this.staticVisibility;
    }

    /**
     * Changes object's visibility on ARView to it's static visibility.
     */
    public void applyStaticVisibility() {
        setVisible(this.staticVisibility);
        Log.d("TEST_CLICK", ID + " : " + staticVisibility);
}

    @Override
    public void setVisible(boolean a) {
        super.setVisible(a);
        Log.d("TEST_CLICK", ID + " : " + a);
    }

    /**
     * Shows or hides the object in AR and adjusts booleans
     * @param visible visibility
     */
    public void show(boolean visible) {
        setVisible(visible);
        this.staticVisibility = visible;
    }

    /**
     * Gets the last bearing value from node
     * @return bearing in degrees
     */
    public float getLastBearing() {
        return lastBearing;
    }

    /**
     * Calculates translation vector with given angle and distance from node A to node B.
     * A and B are not parameters for this function.
     *
     * @param bearing  Bearing of the node from destination point to target point in degrees.
     * @param distance Distance between two nodes.
     * @return Translation vector. If this vector is applied to node A, It's new position will be the same as node B's position.
     */
    private Vector3f calculateTranslationVector(float bearing, float distance) {

        Vector3f northVector = GPSManager.northVector; //Default north vector.

        Quaternion q = new Quaternion();
        northVector = q.fromAngleAxis((float) Math.toRadians(bearing), new Vector3f(0, -1, 0)).mult(northVector); //North vector is rotated by angle between Node A and Node B's position vectors.

        Log.d("NODE_DEBUG", "North Vector : " + northVector);

        northVector = northVector.mult(distance); //Vector is multiplied by distance because it was a unit vector.

        return northVector;
    }

    /**
     * Updates the ARWorld position of the ImageNode.
     * @param currentLocation Current location of the user.
     */
    void updateWorldPosition(Location currentLocation) {

        Log.d("NODE_DEBUG", "This Location : " + gpsLocation);
        Log.d("NODE_DEBUG", "Current Location : " + currentLocation);

        float distanceToObject = gpsLocation.distanceTo(currentLocation); //In meters

        if (distanceToObject >= 350)
            distanceToObject = 1000000;

        float bearingToObject = GPSManager.bearingFrom(gpsLocation, currentLocation); //In degrees

        this.lastBearing = bearingToObject;

        Log.d("NODE_DEBUG", "North Bearing : " + GPSManager.getRealBearing());
        Log.d("NODE_DEBUG", "Object Bearing : " + bearingToObject);
        Log.d("NODE_DEBUG", "Distance : " + distanceToObject);

        this.speed = currentLocation.getSpeed(); //TODO: Not working. Can use sensor or last position to calculate speed.
        this.direction = currentLocation.getBearing(); //TODO: Not Working. Can use sensor or last position to calculate speed.

        Vector3f translationVector = this.calculateTranslationVector(bearingToObject, distanceToObject); //For updating the position of the node at the new location of the user.

        translationVector.y = -objectHeight; //Set default node height as device's height.

        this.setPosition(translationVector); //Change position to new position.

        Quaternion qt = new Quaternion();

        if (!isStatic) {
            this.setOrientation(qt.fromAngleAxis((float) Math.toRadians(bearing), new Vector3f(0, -1, 0))); //Rotate Image according to it's bearing.
        } else {
            float zcord = this.getPosition().getZ();
            float xcord = this.getPosition().getX();
            float angle =  xcord > 0 ? (float) (Math.atan(zcord / xcord) + Math.PI / 2) : (float) (Math.atan(zcord / xcord) - Math.PI / 2);

            this.setOrientation(qt.fromAngleAxis(angle, new Vector3f(0, -1, 0)));
        }

        Log.d("NODE_DEBUG", this.getID() + " : " +  this.getPosition());
    }

    /**
     * For smoothing movement. Currently in testing stage.
     */
    @Override
    public void preRender() {
        //TODO: Fix for smoothing nodes while moving.
        if (GPSManager.interpolateMotionUsingHeading) {
            Log.d("NODE_DEBUG", this.speed + " : " + this.direction);
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