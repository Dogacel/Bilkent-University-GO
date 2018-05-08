package ar.kudan.eu.kudansimple.gps.location;


import android.location.Location;

public interface PlayLocationListener {
    void parseUpdate(Location l);
}
