package ar.kudan.eu.kudansimple.gps.ar.tools;


import ar.kudan.eu.kudansimple.sensor.tools.Compass;
import eu.kudan.kudan.ARImageNode;

public class DirectionArrow extends ARImageNode {

    private Compass compass;
    private float lastRotation;

    public DirectionArrow(float bearingToObject) {
        super("arrow.png");

        this.compass = compass;
        lastRotation = 0f;



        if (bearingToObject >= -60 && bearingToObject <= 60) {
            this.rotateByDegrees(90, 0, 0, 1);
            this.rotateByDegrees(90, 1, 0, 0);
        } else if (bearingToObject >= 60 ) {
            this.rotateByDegrees(-90, 0, 0, 1);
        } else {
            this.rotateByDegrees(180, 0, 0, 1);
        }

        this.setPosition(0,0,50);
        this.scaleByUniform(0.01f);
    }

}
