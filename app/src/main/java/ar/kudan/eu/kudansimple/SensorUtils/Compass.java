package ar.kudan.eu.kudansimple.SensorUtils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import ar.kudan.eu.kudansimple.SensorUtils.Bearing;

public class Compass implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private Bearing bearing;
    private Bearing activeBearing;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;

    /**
     * Constructor for compass class.
     * @param activity Current activity working.
     * @param bearing A bearing object for storing bearing to north in the beginning..
     */
    public Compass(Activity activity, Bearing bearing) {
        this.bearing = bearing;
        activeBearing = new Bearing();

        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(-1);
        }
    }

    /**
     * Destorys the compass object by unregistering listeners. Bearing won't update after this is done.
     */
    public void destroy() {
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
    }

    /**
     * Updates bearing once after sensor is changed.
     * @param event Sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            mCurrentDegree = -azimuthInDegress;
            if (!bearing.isSet()) {
                bearing.setSet();
                bearing.setDegrees(mCurrentDegree);
                Log.d("COMPASS", "Set bearing to " + bearing);
            }
            //TODO: Change degrees with device orientation. Currently not working properly when device is lift up.
            activeBearing.setDegrees(-mCurrentDegree - 180);
        }
    }

    /**
     * Get current bearing to north fixed.
     * @return fixed bearing to north
     */
    public float getBearing() {
        return mCurrentDegree;
    }

    /**
     * Returns current bearing to north.
     * @return bearing in degrees
     */
    public float getActiveBearing() {
        return activeBearing.getDegrees();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}