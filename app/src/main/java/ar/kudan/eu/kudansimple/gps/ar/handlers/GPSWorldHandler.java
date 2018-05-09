package ar.kudan.eu.kudansimple.gps.ar.handlers;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import ar.kudan.eu.kudansimple.gps.ar.tools.TemplateARNodeManager;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSManager;

/**
 * This class handles the GPS objects around the world;
 */
public class GPSWorldHandler {

    private ArrayList<GPSImageNode> gpsObjectList;
    private ArrayList<GPSImageTemplate> templateList;
    private GPSManager gpsManager;

    /**
     * Constructs an empty GPSWorldHandler object
     *
     * @param gpsManager for managing children objects.
     */
    public GPSWorldHandler(GPSManager gpsManager) {
        gpsObjectList = new ArrayList<>();
        templateList = new ArrayList<>();
        this.gpsManager = gpsManager;
    }

    /**
     * Converts the node templates stored into GPSImageNodes.
     */
    public void convertTemplates() {
        for (GPSImageTemplate template : templateList) {
            gpsObjectList.add(TemplateARNodeManager.generateNodeFromTemplate(template));
        }
        templateList = new ArrayList<>();
    }

    public void saveState() {
        for (GPSImageNode gin : gpsObjectList) {
            templateList.add(TemplateARNodeManager.generateTemplateFromNode(gin));
        }
        gpsObjectList = new ArrayList<>();
    }

    /**
     * Dumps the templates into template list for further use.
     * @param templates Templates to be dumped.
     */
    public void dumpTemplates(GPSImageTemplate... templates) {
        templateList.addAll(Arrays.asList(templates));
    }



    /**
     * addsGPSObject only to list.
     *
     * @param gpsImageNode node
     */
    public void addGPSObject(GPSImageNode gpsImageNode) {
        gpsObjectList.add(gpsImageNode);
    }

    /**
     * Sets GPSManager
     * @param gpsManager new GPSManager
     */
    public void setGpsManager(GPSManager gpsManager) {
        this.gpsManager = gpsManager;
    }

    /**
     * Dumps gps objects to world.
     */
    public void dumpGPSObjects() {
        for (GPSImageNode gin : gpsObjectList) {
            gpsManager.getArWorld().addChild(gin);
        }
    }

    /**
     * Converts states of the image nodes into static visibilities.
     */
    public void convertStates() {
        for (GPSImageNode gin : gpsObjectList) {
            gin.applyStaticVisibility();
        }
    }


    /**
     * Shows all objects in the list
     */
    public void showAll() {
        for (GPSImageTemplate gpsImageNode : templateList) {
            gpsImageNode.show(true);
        }
    }

    /**
     * Hides all objects in the list.
     */
    public void hideAll() {
        for (GPSImageTemplate gpsImageNode : templateList) {
            gpsImageNode.show(false);
        }
    }

    /**
     * Shows a specific node in the list
     *
     * @param gpsImageNode node
     */
    public void show(GPSImageTemplate gpsImageNode) {
        gpsImageNode.show(true);
    }

    /**
     * Shows a specific node in the list by ID
     *
     * @param ID id of the node
     */
    public void show(String ID) {
        for (GPSImageTemplate gpsImageNode : templateList) {
            if (gpsImageNode.getID().equals(ID)) {
                show(gpsImageNode);
            }
        }
    }

    /**
     * Hides a specific node in the list
     *
     * @param gpsImageNode node
     */
    public void hide(GPSImageTemplate gpsImageNode) {
        gpsImageNode.show(false);
    }

    /**
     * Hides a specific node in the list by ID
     *
     * @param ID id of the node
     */
    public void hide(String ID) {
        for (GPSImageTemplate gin : templateList) {
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
    public void showOnly(GPSImageTemplate gpsImageNode) {
        hideAll();
        gpsImageNode.show(true);
    }

    /**
     * Shows only a node in the list by ID
     *
     * @param ID id of the node
     */
    public void showOnly(String ID) {
        hideAll();
        for (GPSImageTemplate gin : templateList) {
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
