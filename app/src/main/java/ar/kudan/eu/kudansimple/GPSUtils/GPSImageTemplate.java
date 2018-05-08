package ar.kudan.eu.kudansimple.GPSUtils;

import android.location.Location;

public class GPSImageTemplate {

    private float bearing;
    private String ID, photoLocation;
    private Location location;
    private boolean isStatic;

    public GPSImageTemplate(String ID, String photoLocation, Location location, float bearing, boolean isStatic) {
        this.ID = ID;
        this.photoLocation = photoLocation;
        this.location = location;
        this.bearing = bearing;
        this.isStatic = isStatic;
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
}