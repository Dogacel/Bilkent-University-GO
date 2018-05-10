package ar.kudan.eu.kudansimple.gps.ar.handlers;


import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;

import ar.kudan.eu.kudansimple.gps.ar.tools.ARHelper;
import ar.kudan.eu.kudansimple.gps.ar.tools.TemplateARNodeManager;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSManager;
import eu.kudan.kudan.ARView;

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

    public ArrayList<GPSImageNode> getGpsObjectList() {
        return this.gpsObjectList;
    }

    /**
     * Sets GPSManager
     *
     * @param gpsManager new GPSManager
     */
    public void setGpsManager(GPSManager gpsManager) {
        this.gpsManager = gpsManager;
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
     * Dumps the templates into template list for further use.
     *
     * @param templates Templates to be dumped.
     */
    public void dumpTemplates(GPSImageTemplate... templates) {
        templateList.addAll(Arrays.asList(templates));
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

    /**
     * Dumps gps objects to world.
     */
    public void dumpGPSObjects() {
        for (GPSImageNode gin : gpsObjectList) {
            gpsManager.getArWorld().addChild(gin);
        }
    }

    /**
     * Saves states of the nodes on the world.
     */
    public void saveState() {
        for (GPSImageNode gin : gpsObjectList) {
            templateList.add(TemplateARNodeManager.generateTemplateFromNode(gin));
        }
        gpsObjectList = new ArrayList<>();
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
     * Shows a specific node in the list
     *
     * @param gpsImageNode node
     */
    private void show(GPSImageTemplate gpsImageNode) {
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
    private void hide(GPSImageTemplate gpsImageNode) {
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
    private void hideAll() {
        for (GPSImageTemplate gpsImageNode : templateList) {
            gpsImageNode.show(false);
        }
    }

    /**
     * Shows only a node in the list
     *
     * @param gpsImageNode node
     */
    private void showOnly(GPSImageTemplate gpsImageNode) {
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
     * Gets focused GPS object but it must be visible.
     *
     * @return Visible focused GPSObject
     */
    public GPSImageNode getFocusedVisibleGPSObject(ARView view, MotionEvent motionEvent) {

        ArrayList<GPSImageNode> list = this.getGpsObjectList();

        for (GPSImageNode node : list) {
            //Log.d("TOUCH_EVENT", );
            if (node.getVisible()) {
                if (ARHelper.isNodeSelected(view, node, motionEvent, 10)) {
                    return node;
                }
            }
        }

        return null;
    }
}
