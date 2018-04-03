package ar.kudan.eu.kudansimple;

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
    public boolean isSet() {
        return isSet;
    }

    /**
     * Returns bearing in degrees.
     * @return bearing to north in degrees.
     */
    public float getDegrees() {
        return degrees;
    }

    /**
     * Sets whether degrees is set or not.
     * @param set
     */
    public void setSet(boolean set) {
        this.isSet = set;
    }

    /**
     * Set degrees of bearing.
     * @param degrees bearing to north in degrees.
     */
    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }
}
