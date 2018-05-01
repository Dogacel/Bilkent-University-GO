package ar.kudan.eu.kudansimple;

/**
 * Bearing wrapper class for adjusting ARWorld's translation vector to true north.
 */
class Bearing {
    private float degrees;
    private boolean isSet;

    /**
     * Constructor for bearing object.
     */
    Bearing() {
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
     * Sets whether degrees is set or not.
     * @param set set
     */
    void setSet(boolean set) {
        this.isSet = set;
    }

    /**
     * Returns bearing in degrees.
     * @return bearing to north in degrees.
     */
    float getDegrees() {
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
