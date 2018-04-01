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

import java.util.Timer;
import java.util.TimerTask;

import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARArbiTrack;
import eu.kudan.kudan.ARGyroPlaceManager;
import eu.kudan.kudan.ARImageNode;
import eu.kudan.kudan.ARWorld;

public class MainActivity extends ARActivity implements GestureDetector.OnGestureListener {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private static String accelData, gyroData, compassData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String skey = "jZGcfDzRtoNpR6eCGWzQalY7udjOAIHrdIXwiQTFopkqjf9bh0nORhUoH/kS1Y28yDZNKKlM5kYjLCNuBx772wZbSQpyS+3AIqr10vEDMbOyJi3pzsAzim7o6zw6dT4rFDqN0BTAivKULfJol3sbJhyp+PWSjZKN/wLKoDzRspL24JTboYqJ1SXsseSTaoqDqdnrTZ2R0TKKciGlxYy3HB+Js8l9miRVGGk6p8Y0bkldc54gl5Bj2txesMqXhBE8nY1RKpVHZsmJxKEDntPjCvWYtZwNIlAMO+Q3XLvbNOU+XFCbvHcPILH3wX6jkIKd93xGtd08hp0lz1yr7rAuamF/hezem9LsCgXQqYJt8WnQTt1soKHcc3Wt7GnTFn/8CBCF6P6NQGlXeyixNcz4L42hkJAFfb+k0pBNu25eQloCirbAr3gEIXy/yYMYxtvRjKOo6X3JLgtZtMqZFLd8ygGgArlvi/c3PiBwgSLtl949ihRVmfPybclIb42enKCWDu4xRHl5mHDH4Cs6giWMURRQltl3bSRhb7IB3FVyVUitTXxV/pUSb8ywuVjla8KoJFsHOdFDKZrbDfWoQmCiYE7/KozlVjRdkajmEY00Uvgbvn+9NDuSItS8vzTtMmbiqIdMpdSCzBgeEkhe9k+F1zkdkeN40YOQsxVV4lT3Zh0=";
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey(skey);
    }

    GestureDetectorCompat gestureDetector;
    @Override
    public void setup() {
        super.setup();
        Log.d("IMPO", "Startdgc");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } //endif

        gestureDetector = new GestureDetectorCompat(this, this);
        ARWorld currentWorld = new ARWorld();


        GPSManager gpsManager = new GPSManager(currentWorld, this);
        gpsManager.start();

        getARView().getContentViewPort().getCamera().addChild(currentWorld);


        GPSNode test;
        Location testLocation;

        //testLocation = new Location("dummyprovider");
        //testLocation.setLatitude(39.870011);
        //testLocation.setLongitude(32.749385);




        //test = new GPSNode("LibraryTarget.png", testLocation, -90);
        //gpsManager.getArWorld().addChild(test);

        //test.scaleByUniform(0.05f);
        //test.setVisible(true);

        Location northL = new Location("dummyprovider");
        northL.setLongitude(90.0);
        northL.setLatitude(0.0);
        GPSNode north = new GPSNode("North.png", northL, 90);
        gpsManager.getArWorld().addChild(north);
        north.scaleByUniform(0.05f);
        north.setVisible(true);

        Location southL = new Location("dummyprovider");
        southL.setLongitude(-90.0);
        southL.setLatitude(0.0);
        GPSNode south = new GPSNode("South.png", southL, 90);
        gpsManager.getArWorld().addChild(south);
        south.scaleByUniform(0.05f);
        south.setVisible(true);

        Location westL = new Location("dummyprovider");
        westL.setLongitude(39.854630);
        westL.setLatitude( -2.884413);
        GPSNode west = new GPSNode("West.png", westL, 90);
        gpsManager.getArWorld().addChild(west);
        west.scaleByUniform(0.05f);
        west.setVisible(true);

        Location eastL = new Location("dummyprovider");
        eastL.setLongitude(40.064261);
        eastL.setLatitude(53.971932);
        GPSNode east = new GPSNode("East.png", eastL, 90);
        gpsManager.getArWorld().addChild(east);
        east.scaleByUniform(0.05f);
        east.setVisible(true);

        /**
         new Timer().scheduleAtFixedRate(new TimerTask() {
        @Override public void run() {
        Log.d("INFO", "Node_position: " + trackingNode.getFullPosition());
        }
        }, 0, 1000);//put here time 1000 milliseconds=1 second
         **/
    }

    //Testing
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d("GPS", "Tapped");
        GPSManager.interpolateMotionUsingHeading = ! GPSManager.interpolateMotionUsingHeading;
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


}