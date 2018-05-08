package ar.kudan.eu.kudansimple.GPSUtils;


import android.util.Log;

import java.util.ArrayList;

import ar.kudan.eu.kudansimple.GPSUtils.GPSImageNode;
import ar.kudan.eu.kudansimple.GPSUtils.GPSManager;

/**
 * This class handles the GPS objects around the world;
 */
public class GPSWorldHandler {

    private ArrayList<GPSImageNode> gpsObjectList;
    private GPSManager gpsManager;

    /**
     * Constructs an empty GPSWorldHandler object
     *
     * @param gpsManager for managing children objects.
     */
    public GPSWorldHandler(GPSManager gpsManager) {
        gpsObjectList = new ArrayList<>();
        this.gpsManager = gpsManager;
    }

    /**
     * addsGPSObject only to list.
     *
     * @param gpsImageNode node
     */
    void addGPSObject(GPSImageNode gpsImageNode) {
        gpsObjectList.add(gpsImageNode);
    }

    /**
     * Adds object to both gpsmanager and the list.
     *
     * @param gpsImageNode node
     */
    public void addGPSObjectCumilative(GPSImageNode gpsImageNode) {
        addGPSObject(gpsImageNode);
        gpsManager.getArWorld().addChild(gpsImageNode);
    }

    /**
     * Shows all objects in the list
     */
    public void showAll() {
        for (GPSImageNode gpsImageNode : gpsObjectList) {
            gpsImageNode.setVisible(true);
        }
    }

    /**
     * Hides all objects in the list.
     */
    public void hideAll() {
        for (GPSImageNode gpsImageNode : gpsObjectList) {
            gpsImageNode.setVisible(false);
        }
    }

    /**
     * Shows a specific node in the list
     *
     * @param gpsImageNode node
     */
    public void show(GPSImageNode gpsImageNode) {
        gpsImageNode.setVisible(true);
    }

    /**
     * Shows a specific node in the list by ID
     *
     * @param ID id of the node
     */
    public void show(String ID) {
        for (GPSImageNode gin : gpsObjectList) {
            if (gin.getID().equals(ID)) {
                show(gin);
            }
        }
    }

    /**
     * Hides a specific node in the list
     *
     * @param gpsImageNode node
     */
    public void hide(GPSImageNode gpsImageNode) {
        gpsImageNode.setVisible(false);
    }

    /**
     * Hides a specific node in the list by ID
     *
     * @param ID id of the node
     */
    public void hide(String ID) {
        for (GPSImageNode gin : gpsObjectList) {
            if (gin.getID().equals(ID)) {
                hide(gin);
            }
        }
    }

    /**
     * Shows only a node in the list
     *
     * @param gpsImageNode node
     */
    public void showOnly(GPSImageNode gpsImageNode) {
        hideAll();
        gpsImageNode.setVisible(true);
    }

    /**
     * Shows only a node in the list by ID
     *
     * @param ID id of the node
     */
    public void showOnly(String ID) {
        hideAll();
        for (GPSImageNode gin : gpsObjectList) {
            if (gin.getID().equals(ID)) {
                show(gin);
            }
        }
    }

    /**
     * Gets the focused GPS Object in the list.
     *
     * @return focused gps object
     */
    public GPSImageNode getFocusedGPSObject() {
        if (gpsObjectList.size() == 0)
            return null;

        float activeBearing = gpsManager.getBearingToNorth();

        GPSImageNode tmp = gpsObjectList.get(0);

        Log.d("TOUCH_EVENT", activeBearing + "");

        for (GPSImageNode gin : gpsObjectList) {

            float ginBearing = gin.getLastBearing() < 0 ? 180 + gin.getLastBearing() : 360 - gin.getLastBearing();
            float tmpBearing = tmp.getLastBearing() < 0 ? 180 + tmp.getLastBearing() : 360 - tmp.getLastBearing();

            Log.d("TOUCH_EVENT", gin.getID() + ": " + ginBearing);

            float shortestAngleCurrent = Math.min(Math.abs(activeBearing - ginBearing), Math.abs(360 - (activeBearing - ginBearing)));
            float shortestAnglePast    = Math.min(Math.abs(activeBearing - tmpBearing), Math.abs(360 - (activeBearing - tmpBearing)));

            if (shortestAngleCurrent < shortestAnglePast) {
                tmp = gin;
            }
        }

        return tmp;
    }

    /**
     * Gets focused GPS object but it must be visible.
     *
     * @return Visible focused GPSObject
     */
    public GPSImageNode getFocusedVisibleGPSObject() {
        if (gpsObjectList.size() == 0)
            return null;

        float activeBearing = gpsManager.getBearingToNorth();

        GPSImageNode tmp = null;

        for (GPSImageNode gin : gpsObjectList) {
            if (gin.getVisible()) {
                tmp = gin;
                break;
            }
        }

        if (tmp == null)
            return null;

        Log.d("TOUCH_EVENT", activeBearing + "");

        for (GPSImageNode gin : gpsObjectList) {
            if (gin.getVisible()) {
                float ginBearing = gin.getLastBearing() < 0 ? 180 + gin.getLastBearing() : 360 - gin.getLastBearing();
                float tmpBearing = tmp.getLastBearing() < 0 ? 180 + tmp.getLastBearing() : 360 - tmp.getLastBearing();

                Log.d("TOUCH_EVENT", gin.getID() + ": " + ginBearing);

                float shortestAngleCurrent = Math.min(Math.abs(activeBearing - ginBearing), Math.abs(360 - (activeBearing - ginBearing)));
                float shortestAnglePast    = Math.min(Math.abs(activeBearing - tmpBearing), Math.abs(360 - (activeBearing - tmpBearing)));

                if (shortestAngleCurrent < shortestAnglePast) {
                    tmp = gin;
                }

            }
        }

        return tmp;
    }
}
