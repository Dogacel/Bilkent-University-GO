package ar.kudan.eu.kudansimple.sensor.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hoan.dsensor_master.DProcessedSensor;
import com.hoan.dsensor_master.DSensorEvent;
import com.hoan.dsensor_master.DSensorManager;
import com.hoan.dsensor_master.interfaces.DProcessedEventListener;

import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.sensor.Bearing;

public class Compass {

    public static Compass instance;

    private Bearing currentBearing;


    /**
     * Constructor for compass class.
     *
     * @param activity           Current context working.
     */
    public Compass(Context activity) {
        currentBearing = new Bearing();

        DSensorManager.startDProcessedSensor(activity.getApplicationContext(), DProcessedSensor.TYPE_3D_COMPASS,
                new DProcessedEventListener() {
                    @Override
                    public void onProcessedValueChanged(DSensorEvent dSensorEvent) {
                        float degrees = (float) Math.toDegrees(dSensorEvent.values[0]);
                        currentBearing.setDegrees(degrees > 0 ? degrees : 360 + degrees);
                        Log.d("COMPASS", "3D : " + currentBearing.getDegrees());
                    }
                });

    }

    /**
     * Destroys the compass object by unregistering listeners. Bearing won't update after this is done.
     */
    public void destroy() {
        DSensorManager.stopDSensor();
    }

    public static void init(Context a) {
        instance = new Compass(a);
    }

    public static Compass getInstance() {
        return instance;
    }

    /**
     * Returns current fixedNorthBearing to north.
     *
     * @return fixedNorthBearing in degrees
     */
    public float getCurrentBearing() {
        return currentBearing.getDegrees();
    }


}