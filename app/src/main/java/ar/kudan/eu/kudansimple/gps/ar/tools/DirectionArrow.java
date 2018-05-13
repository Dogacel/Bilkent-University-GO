package ar.kudan.eu.kudansimple.gps.ar.tools;


import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import ar.kudan.eu.kudansimple.sensor.tools.Compass;
import eu.kudan.kudan.ARImageNode;

public class DirectionArrow extends ARImageNode {

    private boolean rotated;
    private GPSImageNode node;

    public DirectionArrow(GPSImageNode node) {
        super("arrow.png");

        rotated = false;
        this.node = node;

        this.setPosition(0,0,50);
        this.scaleByUniform(0.01f);
    }

     @Override
    public void preRender() {
        if (false) {
            if (node.getLastBearing() != 550) {
                Quaternion qt = new Quaternion();
                if (node.getLastBearing() >= -60 && node.getLastBearing() <= 60) {
                    this.setOrientation(qt.fromAngleAxis((float) Math.toRadians(90), new Vector3f(0, -1, 0))); //Rotate Image according to it's bearing.
                    //this.rotateByDegrees(90, 0, 0, 1);
                    //this.rotateByDegrees(90, 1, 0, 0);
                } else if (node.getLastBearing() >= 60) {
                    this.setOrientation(qt.fromAngleAxis((float) Math.toRadians(-90), new Vector3f(0, -1, 0))); //Rotate Image according to it's bearing.
                    //this.rotateByDegrees(-90, 0, 0, 1);
                } else {
                    this.setOrientation(qt.fromAngleAxis((float) Math.toRadians(180), new Vector3f(0, -1, 0))); //Rotate Image according to it's bearing.
                    //this.rotateByDegrees(180, 0, 0, 1);
                }
                rotated = true;
            }
        }
    }

}
