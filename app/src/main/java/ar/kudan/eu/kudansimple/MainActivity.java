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

        Log.d("MAINACTIVITY", "Started !");

        //TODO: Permission checking is not working at the moment. Make a popup show for enabling camera and location permission.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } //endif

        //For debug purposes.
        gestureDetector = new GestureDetectorCompat(this, this);

        //Init a new world.
        ARWorld currentWorld = new ARWorld();

        //Start GPSManager
        GPSManager gpsManager = new GPSManager(currentWorld, this);
        gpsManager.start();

        //Add current world to our camera.
        getARView().getContentViewPort().getCamera().addChild(currentWorld);

        //North Node
        Location northL = new Location("dummyprovider");
        northL.setLatitude(39.866699);
        northL.setLongitude(32.748784);
        GPSImageNode north = new GPSImageNode("North.png", northL, 90);
        gpsManager.getArWorld().addChild(north);
        north.scaleByUniform(0.05f);
        north.setVisible(true);


        //South Node
        Location southL = new Location("dummyprovider");
        southL.setLatitude(39.861362);
        southL.setLongitude(32.748870);
        GPSImageNode south = new GPSImageNode("South.png", southL, -90);
        gpsManager.getArWorld().addChild(south);
        south.scaleByUniform(0.05f);
        south.setVisible(true);


        //West Node
        Location westL = new Location("dummyprovider");
        westL.setLatitude(39.864003);
        westL.setLongitude( 32.744766);
        GPSImageNode west = new GPSImageNode("West.png", westL, 0);
        gpsManager.getArWorld().addChild(west);
        west.scaleByUniform(0.05f);
        west.setVisible(true);


        //East Node
        Location eastL = new Location("dummyprovider");
        eastL.setLatitude(39.863903);
        eastL.setLongitude(32.751890);
        GPSImageNode east = new GPSImageNode("East.png", eastL, 180);
        gpsManager.getArWorld().addChild(east);
        east.scaleByUniform(0.05f);
        east.setVisible(true);
    }

    //Testing

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d("MAINACTIVITY", "Tapped");
        //Useless, for testing only.
        GPSManager.interpolateMotionUsingHeading = ! GPSManager.interpolateMotionUsingHeading;
        return false;
    }

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