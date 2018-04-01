package ar.kudan.eu.kudansimple;

/**
 * Created by Dogacel on 4/1/2018.
 */

public class Bearing {
    private float degrees;
    private boolean isSet;

    public Bearing() {
        isSet = false;
    }

    public boolean isSet() {
        return isSet;
    }

    public float getDegrees() {
        return degrees;
    }

    public void setSet(boolean set) {
        this.isSet = set;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }
}
