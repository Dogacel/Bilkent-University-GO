package ar.kudan.eu.kudansimple.GPSUtils;

/**
 * Created by Faruk Balcı on 8.5.2018.
 * Contains infornation needed for AR purposes.
 */

public class NodeARInformation {

    private String label;
    private double x;
    private double y;

    public NodeARInformation( String label, double xPos, double yPos) {
        this.label = label;
        x = xPos;
        y = yPos;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }



}
