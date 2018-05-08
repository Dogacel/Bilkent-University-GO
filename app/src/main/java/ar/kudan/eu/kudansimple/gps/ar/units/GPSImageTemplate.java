package ar.kudan.eu.kudansimple.gps.ar.units;

import android.location.Location;

public class GPSImageTemplate {

    private float bearing, height;
    private String ID, photoLocation;
    private Location location;
    private boolean isStatic;

    private float scale;
    private boolean visible;

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

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public float getScale() {
        return scale;
    }

    public void scaleByUniform(float scale) {
        this.scale = scale;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}