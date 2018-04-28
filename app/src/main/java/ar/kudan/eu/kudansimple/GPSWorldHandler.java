package ar.kudan.eu.kudansimple;


import android.util.Pair;

import java.util.ArrayList;

/**
 * This class handles the GPS objects around the world;
 */

public class GPSWorldHandler {

    private ArrayList<GPSImageNode> gpsObjectList;

    public GPSWorldHandler(GPSManager gpsManager) {
        gpsObjectList = new ArrayList<>();
    }

    public void addGPSObject(GPSImageNode gpsImageNode) {
        gpsObjectList.add(gpsImageNode);
    }

    public void showAll() {
        for (GPSImageNode gpsImageNode : gpsObjectList) {
            gpsImageNode.setVisible(true);
        }
    }

    public void hideAll() {
        for (GPSImageNode gpsImageNode : gpsObjectList) {
            gpsImageNode.setVisible(false);
        }
    }

    public void show(GPSImageNode gpsImageNode) {
        gpsImageNode.setVisible(true);
    }

    public void hide(GPSImageNode gpsImageNode) {
        gpsImageNode.setVisible(false);
    }

    public void showOnly(GPSImageNode gpsImageNode) {
        hideAll();
        gpsImageNode.setVisible(true);
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
