package ar.kudan.eu.kudansimple;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARArbiTrack;
import eu.kudan.kudan.ARGyroPlaceManager;
import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARWorld;

public class MainActivity extends ARActivity implements GestureDetector.OnGestureListener{

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private static String accelData, gyroData, compassData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gestureDetector = new GestureDetectorCompat(this, this);
        super.onCreate(savedInstanceState);
        String skey = "jZGcfDzRtoNpR6eCGWzQalY7udjOAIHrdIXwiQTFopkqjf9bh0nORhUoH/kS1Y28yDZNKKlM5kYjLCNuBx772wZbSQpyS+3AIqr10vEDMbOyJi3pzsAzim7o6zw6dT4rFDqN0BTAivKULfJol3sbJhyp+PWSjZKN/wLKoDzRspL24JTboYqJ1SXsseSTaoqDqdnrTZ2R0TKKciGlxYy3HB+Js8l9miRVGGk6p8Y0bkldc54gl5Bj2txesMqXhBE8nY1RKpVHZsmJxKEDntPjCvWYtZwNIlAMO+Q3XLvbNOU+XFCbvHcPILH3wX6jkIKd93xGtd08hp0lz1yr7rAuamF/hezem9LsCgXQqYJt8WnQTt1soKHcc3Wt7GnTFn/8CBCF6P6NQGlXeyixNcz4L42hkJAFfb+k0pBNu25eQloCirbAr3gEIXy/yYMYxtvRjKOo6X3JLgtZtMqZFLd8ygGgArlvi/c3PiBwgSLtl949ihRVmfPybclIb42enKCWDu4xRHl5mHDH4Cs6giWMURRQltl3bSRhb7IB3FVyVUitTXxV/pUSb8ywuVjla8KoJFsHOdFDKZrbDfWoQmCiYE7/KozlVjRdkajmEY00Uvgbvn+9NDuSItS8vzTtMmbiqIdMpdSCzBgeEkhe9k+F1zkdkeN40YOQsxVV4lT3Zh0=";
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey(skey);
    }


    @Override
    public void setup()
    {
        super.setup();
        Log.d("IMPO", "Startdgc");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } //endif

        ARWorld currentWorld = new ARWorld();
        GPSManager gpsManager = new GPSManager(currentWorld);

        ARArbiTrack arArbiTrack = ARArbiTrack.getInstance();
        arArbiTrack.initialise();

        ARGyroPlaceManager gyroPlaceManager = ARGyroPlaceManager.getInstance();
        gyroPlaceManager.initialise();

        // Create a node to be used as the target.
        ARImageNode targetNode = new ARImageNode("Cow Target.png");
        ARImageNode trackingNode = new ARImageNode("Cow Tracking.png");

        // Add it to the Gyro Placement Manager's world so that  it moves with the device's Gyroscope.
        gyroPlaceManager.getWorld().addChild(targetNode);

        // Rotate and scale the node to ensure it is displayed correctly.
        targetNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        targetNode.rotateByDegrees(180.0f, 0.0f, 1.0f, 0.0f);

        targetNode.scaleByUniform(0.3f);
        // targetNode.setPosition(20.0f, 20.0f, 20.0f);
        // Set the ArbiTracker's target node.

        arArbiTrack.setTargetNode(targetNode);

        trackingNode.rotateByDegrees(90.0f, 1.0f, 0.0f, 0.0f);
        trackingNode.rotateByDegrees(180.0f, 10.0f, 1.0f, 0.0f);

        arArbiTrack.getWorld().addChild(trackingNode);


    }

    private GestureDetectorCompat gestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        ARArbiTrack arbiTrack = ARArbiTrack.getInstance();
        if (arbiTrack.getIsTracking()) {

            arbiTrack.stop();
            arbiTrack.getTargetNode().setVisible(true);
        }

        else {
            arbiTrack.start();
            arbiTrack.getTargetNode().setVisible(false);
        }

        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {return false;}

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float f1, float f2) {return false;}

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float f1, float f2) {return false;}
}
