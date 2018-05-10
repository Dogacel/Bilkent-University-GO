package ar.kudan.eu.kudansimple.gps.ar.tools;


import ar.kudan.eu.kudansimple.sensor.tools.Compass;
import eu.kudan.kudan.ARImageNode;

public class DirectionArrow extends ARImageNode {

    private Compass compass;
    private float lastRotation;

    public DirectionArrow(Compass compass) {
        super("arrow.png");

        this.compass = compass;
        lastRotation = 0f;

        this.rotateByDegrees(180, 0, 0, 1);

        this.setPosition(0,0,50);
        this.scaleByUniform(0.01f);
    }

    @Override
    public void preRender() {
        super.preRender();
        float angle = compass.getCurrentBearing();

        //this.rotateByDegrees(angle - lastRotation, 0, 1, 0);
        lastRotation = angle;
    }

}
