package ar.kudan.eu.kudansimple.GPSUtils.LocationUtils;


import android.location.Location;

public interface PlayLocationListener {
    void parseUpdate(Location l);
}
