package ar.kudan.eu.kudansimple;

import android.location.Location;
import android.util.Log;

import eu.kudan.kudan.ARNode;

/**
 * Created by dogacel on 21.03.2018.
 */

public class GPSNode extends ARNode {

    private Location gpsLocation;

    public GPSNode (Location location) {
        this.gpsLocation = location;
    }

    public Location getGpsLocation() {
        return this.gpsLocation;
    }

    public boolean setGpsLocation(Location l) {
        try {
            this.gpsLocation = l;
        } catch (Exception e){
            Log.e("ERROR", "setGpsLocation: ", e);
            return false;
        }
        return true;
    }
}