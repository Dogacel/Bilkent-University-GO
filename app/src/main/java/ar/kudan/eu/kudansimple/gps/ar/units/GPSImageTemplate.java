package ar.kudan.eu.kudansimple.gps.ar.units;

import android.location.Location;

/**
 * Template class for the GPSImageNode's
 */
public class GPSImageTemplate {

    private float bearing, height;
    private String ID, photoLocation;
    private Location location;
    private boolean isStatic;

    private float scale;
    private boolean visible;

    /**
     * GPSImageNode template for initiliasing in the ARView.
     * @param ID ID of the node.
     * @param photoLocation Image location of the node.
     * @param location GPS location of the world.
     * @param height Height of the node.
     * @param bearing Bearing of the node.
     * @param isStatic Is the image static ?
     */
    public GPSImageTemplate(String ID, String photoLocation, Location location, float height, float bearing, boolean isStatic) {
        this.ID = ID;
        this.photoLocation = photoLocation;
        this.location = location;
        this.height = height;
        this.bearing = bearing;
        this.isStatic = isStatic;
        scale = 1;
        visible = true;
    }

    /**
     * Gets height of the object.
     * @return Height of the object
     */
    public float getHeight() {
        return height;
    }

    /**
     * Set height of the object.
     * @param height height.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Get bearing of the object's image to the north.
     * @return bearing in degrees.
     */
    public float getBearing() {
        return bearing;
    }

    /**
     * Sets image's bearing
     * @param bearing bearing in degrees.
     */
    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    /**
     * Gets ID for the node.
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Set ID of the node.
     * @param ID ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns the location of the photo.
     * @return photo location.
     */
    public String getPhotoLocation() {
        return photoLocation;
    }

    /**
     * Sets photo location
     * @param photoLocation new photo location.
     */
    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    /**
     * Get the location of the node.
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set location of the node.
     * @param location new location of the node.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get whether the node image is static or not.
     * @return static
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * Set whether an image of the node is static or not.
     * @param aStatic static
     */
    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    /**
     * Return scale of the image
     * @return scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * Scale the node
     * @param scale scale in float.
     */
    public void scaleByUniform(float scale) {
        this.scale = scale;
    }

    /**
     * Is the node visible ?
     * @return is visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set node's visibility.
     * @param visible new visibility.
     */
    public void show(boolean visible) {
        this.visible = visible;
    }
}