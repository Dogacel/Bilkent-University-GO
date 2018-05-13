package ar.kudan.eu.kudansimple.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;


import java.util.ArrayList;

import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.ContainerManager;
import ar.kudan.eu.kudansimple.gps.ar.tools.ARHelper;
import ar.kudan.eu.kudansimple.gps.ar.tools.DirectionArrow;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageNode;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSManager;
import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;
import ar.kudan.eu.kudansimple.sensor.tools.Compass;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARView;
import eu.kudan.kudan.ARWorld;

/**
 * ARView for showing objects around the world using camera.
 */
public class ARViewActivity extends ARActivity implements GestureDetector.OnGestureListener {

    public static final String IS_SOURCE = "ARViewActivity is the source for this intent";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA_AND_FINE_LOCATION = 100;
    private GestureDetectorCompat gestureDetector;

    private GPSWorldHandler gpsWorldHandler;
    private GPSManager gpsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*String defaultKey = "jZGcfDzRtoNpR6eCGWzQalY7udjOAIHrdIXwiQTFopkqjf9bh0nORhUoH/kS1Y28yDZNKKlM5kYjLCNuBx772wZbSQpyS+3AIqr10vEDMbOyJi3pzsAzim7o6zw6dT4rFDqN0BTAivKULfJol3sbJhyp+PWSjZKN/wLKoDzRspL24JTboYqJ1SXsseSTaoqDqdnrTZ2R0TKKciGlxYy3HB+Js8l9miRVGGk6p8Y0bkldc54gl5Bj2txesMqXhBE8nY1RKpVHZsmJxKEDntPjCvWYtZwNIlAMO+Q3XLvbNOU+XFCbvHcPILH3wX6jkIKd93xGtd08hp0lz1yr7rAuamF/hezem9LsCgXQqYJt8WnQTt1soKHcc3Wt7GnTFn/8CBCF6P6NQGlXeyixNcz4L42hkJAFfb+k0pBNu25eQloCirbAr3gEIXy/yYMYxtvRjKOo6X3JLgtZtMqZFLd8ygGgArlvi/c3PiBwgSLtl949ihRVmfPybclIb42enKCWDu4xRHl5mHDH4Cs6giWMURRQltl3bSRhb7IB3FVyVUitTXxV/pUSb8ywuVjla8KoJFsHOdFDKZrbDfWoQmCiYE7/KozlVjRdkajmEY00Uvgbvn+9NDuSItS8vzTtMmbiqIdMpdSCzBgeEkhe9k+F1zkdkeN40YOQsxVV4lT3Zh0=";
        ARAPIKey key = ARAPIKey.getInstance();
        //key.setAPIKey(defaultKey); */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Compass.getInstance() != null) {
            Compass.getInstance().destroy();
        }
        if (gpsManager != null) {
            gpsManager.destroy();
        }
        if (gpsWorldHandler != null) {
            gpsWorldHandler.saveState();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("AR_VIEW_ACTIVITY", "Started !");

        //Check and request camera permission.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(getApplicationContext(), "Please Give Permission!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_CAMERA_AND_FINE_LOCATION);
        }
    }

    @Override
    public void setup() {
        super.setup();

        Compass.init(this.getApplicationContext());

        //only attempt to setup if the permissions are granted!
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupARActivity();
        } else {
            onBackPressed();
        }
    }

    /*
     * Note: body of this method used to be the the body of the setup() method.
     */
    private void setupARActivity() {

        Log.d("MAIN_ACTIVITY", "Started !");

        //For testing.
        gestureDetector = new GestureDetectorCompat(this, this);

        //Init a new world.
        ARWorld currentWorld = new ARWorld();

        getARView().getContentViewPort().getCamera().addChild(currentWorld);

        //Start GPSManager
        gpsManager = new GPSManager(currentWorld, this);
        gpsManager.start();


        //Initializes the world from the preloaded templates.
        gpsWorldHandler = ContainerManager.getInstance().getGpsWorldHandler();
        gpsWorldHandler.setGpsManager(gpsManager);
        gpsWorldHandler.convertTemplates();
        gpsWorldHandler.dumpGPSObjects();
        gpsWorldHandler.convertStates();



        /*
        TODO: Arrow

        String id = getIntent().getStringExtra(Constants.EXTRA_MESSAGE_BUILDING);

        if (id != null) {
            DirectionArrow arrow = new DirectionArrow(gpsWorldHandler.getNodeFromID(id));
            arrow.setVisible(true);
            currentWorld.addChild(arrow);
        }

        */
    }

    //Testing

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
            Log.d("TOUCH_EVENT", "TOUCH START -------");
            gestureDetector.onTouchEvent(motionEvent);

            String id = gpsWorldHandler.getFocusedVisibleGPSObject(this.getARView(), motionEvent).getID();

            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

            if (id != null && gpsManager.getCurrentLocation() != null) {
                Intent intent = new Intent(this, BuildingInfoActivity.class);
                intent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, ARViewActivity.IS_SOURCE);
                intent.putExtra(Constants.EXTRA_MESSAGE_BUILDING, Integer.parseInt(id));
                startActivity(intent);
            }

            Log.d("TOUCH_EVENT", "TOUCH END ----------");


        }
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d("AR_VIEW_ACTIVITY", "Tapped");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA_AND_FINE_LOCATION) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Permissions granted!
                //We are recreating here because onCreate() method of ARActivity class checks for camera permission.
                recreate();
            } else {
                //permission not granted close the activity.
                Toast.makeText(getApplicationContext(), "Camera and Location permissions are needed for AR functions!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
