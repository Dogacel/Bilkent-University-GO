package ar.kudan.eu.kudansimple.gps.ar.tools;

import android.location.Location;
import android.util.Log;

import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;

public class TemplateARNodeManager {

    /**
     * Generates a node from the template.
     *
     * @param template Template to be converted.
     * @return GPSImageNode generated from the node.
     */
    public static GPSImageNode generateNodeFromTemplate(GPSImageTemplate template) {
        String ID = template.getID();
        String pic = template.getPhotoLocation();
        Location location = template.getLocation();
        float height = template.getHeight();
        float bearing = template.getBearing();
        boolean isStatic = template.isStatic();

        GPSImageNode gin = new GPSImageNode(ID, pic, location, height, bearing, isStatic);
        gin.scaleByUniform(template.getScale());
        gin.setStaticVisibility(template.isVisible());
        Log.d("TEST_CLICK", ID + template.isVisible());

        if (template.isForceShow())
            gin.forceShow();
        return gin;
    }

    /**
     * Generates a GPSImageTemplate from node.
     *
     * @param template node to be saved
     * @return saved template.
     */
    public static GPSImageTemplate generateTemplateFromNode(GPSImageNode template) {
        String ID = template.getID();
        String pic = template.getPhoto();
        Location location = template.getGpsLocation();
        float height = template.getObjectHeight();
        float bearing = template.getBearing();
        boolean isStatic = template.isStatic();

        GPSImageTemplate gin = new GPSImageTemplate(ID, pic, location, -height, bearing, isStatic);
        gin.scaleByUniform(template.getLastScale());
        gin.show(template.getStaticVisibility());

        return gin;
    }
}
