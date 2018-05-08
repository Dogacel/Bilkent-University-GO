package ar.kudan.eu.kudansimple.gps.ar.tools;

import android.location.Location;

import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;

public class TemplateARNodeManager {

    /**
     * Generates a node from the template.
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
        gin.setVisible(template.isVisible());

        return gin;
    }
}
