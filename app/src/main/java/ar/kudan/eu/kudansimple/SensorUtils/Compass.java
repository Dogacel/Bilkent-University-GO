package ar.kudan.eu.kudansimple.SensorUtils;

import android.app.Activity;
import android.util.Log;

import com.hoan.dsensor_master.DProcessedSensor;
import com.hoan.dsensor_master.DSensorEvent;
import com.hoan.dsensor_master.DSensorManager;
import com.hoan.dsensor_master.interfaces.DProcessedEventListener;

public class Compass {

    private Bearing fixedNorthBearing;
    private Bearing currentBearing;

    /**
     * Constructor for compass class.
     * @param activity Current activity working.
     * @param fixedNorthBearing A fixedNorthBearing object for storing fixedNorthBearing to north in the beginning.
     */
    public Compass(Activity activity, final Bearing fixedNorthBearing) {
        this.fixedNorthBearing = fixedNorthBearing;
        currentBearing = new Bearing();

        DSensorManager.startDProcessedSensor(activity.getApplicationContext(), DProcessedSensor.TYPE_3D_COMPASS,
                new DProcessedEventListener() {
                    @Override
                    public void onProcessedValueChanged(DSensorEvent dSensorEvent) {
                        float degrees =  (float) Math.toDegrees(dSensorEvent.values[0]);
                        currentBearing.setDegrees(degrees > 0 ? degrees : 360 + degrees);
                        Log.d("COMPASS", "3D : " + currentBearing.getDegrees());

                        if (!fixedNorthBearing.isSet()) {
                            fixedNorthBearing.setSet();
                            fixedNorthBearing.setDegrees(-currentBearing.getDegrees());
                            Log.d("COMPASS", "Set fixedNorthBearing to " + fixedNorthBearing);
                        }
                    }
                });

    }

    /**
     * Destorys the compass object by unregistering listeners. Bearing won't update after this is done.
     */
    public void destroy() {
        DSensorManager.stopDSensor();
    }

    /**
     * Get current fixedNorthBearing to north fixed.
     * @return fixed fixedNorthBearing to north
     */
    public float getFixedNorthBearing() {
        return fixedNorthBearing.getDegrees();
    }

    /**
     * Returns current fixedNorthBearing to north.
     * @return fixedNorthBearing in degrees
     */
    public float getCurrentBearing() {
        return currentBearing.getDegrees();
    }


}