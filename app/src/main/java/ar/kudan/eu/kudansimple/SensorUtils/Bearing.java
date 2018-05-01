package ar.kudan.eu.kudansimple.SensorUtils;

/**
 * Bearing wrapper class for adjusting ARWorld's translation vector to true north.
 */
public class Bearing {
    private float degrees;
    private boolean isSet;

    /**
     * Constructor for bearing object.
     */
    public Bearing() {
        isSet = false;
    }

    /**
     * Returns if Bearing is set or not.
     * @return is it set ?
     */
    boolean isSet() {
        return isSet;
    }

    /**
     * Sets whether degrees is set or not to true.
     */
    void setSet() {
        this.isSet = true;
    }

    /**
     * Returns bearing in degrees.
     * @return bearing to north in degrees.
     */
    public float getDegrees() {
        return degrees;
    }

    /**
     * Set degrees of bearing.
     * @param degrees bearing to north in degrees.
     */
    void setDegrees(float degrees) {
        this.degrees = degrees;
    }
}
