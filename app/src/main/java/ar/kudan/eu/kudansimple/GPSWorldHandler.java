package ar.kudan.eu.kudansimple;


import android.util.Pair;

import java.util.ArrayList;

/**
 * This class handles the GPS objects around the world;
 */

public class GPSWorldHandler {

    private ArrayList<GPSImageNode> gpsObjectList;

    public GPSWorldHandler() {
        gpsObjectList = new ArrayList<>();
    }

    public void addGPSObject(GPSImageNode gpsImageNode) {
        gpsObjectList.add(gpsImageNode);
    }

    public GPSImageNode getFocusedGPSObject() {
        if (gpsObjectList.size() == 0)
            return null;

        Pair bearingMin = new Pair(gpsObjectList.get(0), 0);
        for (GPSImageNode gin: gpsObjectList) {

        }

        return gpsObjectList.get((int) bearingMin.second);
    }
}
