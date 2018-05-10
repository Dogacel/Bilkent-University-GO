package ar.kudan.eu.kudansimple.gps.information;

/*
 * Contains information needed for AR purposes.
 */

public class NodeARInformation {

    private String label;
    private float lat;
    private float lon;

    public NodeARInformation(String label, float xPos, float yPos) {
        this.label = label;
        lat = xPos;
        lon = yPos;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String toString() {
        return label + " , { "  + lon + " , " + lat + " }\n";
    }

}
