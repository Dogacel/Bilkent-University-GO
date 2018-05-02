package ar.kudan.eu.kudansimple;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;


import ar.kudan.eu.kudansimple.GPSUtils.GPSImageNode;
import ar.kudan.eu.kudansimple.GPSUtils.GPSManager;
import ar.kudan.eu.kudansimple.GPSUtils.GPSWorldHandler;
import eu.kudan.kudan.ARAPIKey;
import eu.kudan.kudan.ARActivity;
import eu.kudan.kudan.ARWorld;


public class ARViewActivity extends ARActivity implements GestureDetector.OnGestureListener {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA_AND_FINE_LOCATION = 100;

    private GestureDetectorCompat gestureDetector;

    private GPSWorldHandler gpsWorldHandler;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String defaultKey = "jZGcfDzRtoNpR6eCGWzQalY7udjOAIHrdIXwiQTFopkqjf9bh0nORhUoH/kS1Y28yDZNKKlM5kYjLCNuBx772wZbSQpyS+3AIqr10vEDMbOyJi3pzsAzim7o6zw6dT4rFDqN0BTAivKULfJol3sbJhyp+PWSjZKN/wLKoDzRspL24JTboYqJ1SXsseSTaoqDqdnrTZ2R0TKKciGlxYy3HB+Js8l9miRVGGk6p8Y0bkldc54gl5Bj2txesMqXhBE8nY1RKpVHZsmJxKEDntPjCvWYtZwNIlAMO+Q3XLvbNOU+XFCbvHcPILH3wX6jkIKd93xGtd08hp0lz1yr7rAuamF/hezem9LsCgXQqYJt8WnQTt1soKHcc3Wt7GnTFn/8CBCF6P6NQGlXeyixNcz4L42hkJAFfb+k0pBNu25eQloCirbAr3gEIXy/yYMYxtvRjKOo6X3JLgtZtMqZFLd8ygGgArlvi/c3PiBwgSLtl949ihRVmfPybclIb42enKCWDu4xRHl5mHDH4Cs6giWMURRQltl3bSRhb7IB3FVyVUitTXxV/pUSb8ywuVjla8KoJFsHOdFDKZrbDfWoQmCiYE7/KozlVjRdkajmEY00Uvgbvn+9NDuSItS8vzTtMmbiqIdMpdSCzBgeEkhe9k+F1zkdkeN40YOQsxVV4lT3Zh0=";
        ARAPIKey key = ARAPIKey.getInstance();
        key.setAPIKey(defaultKey);
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

        //only attempt to setup if the permissions are granted!
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupARActivity();
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

        //Start GPSManager
        GPSManager gpsManager = new GPSManager(currentWorld, this);
        gpsManager.start();

        //Add current world to our camera.
        getARView().getContentViewPort().getCamera().addChild(currentWorld);

        gpsWorldHandler = new GPSWorldHandler(gpsManager);

        //North Node
        Location northL = new Location("dummyprovider");
        northL.setLatitude(39.866699);
        northL.setLongitude(32.748784);
        GPSImageNode north = new GPSImageNode("north", "North.png", northL, 0, true);
        gpsWorldHandler.addGPSObjectCumilative(north);
        north.scaleByUniform(0.05f);
        north.setVisible(true);


        //South Node
        Location southL = new Location("dummyprovider");
        southL.setLatitude(39.861362);
        southL.setLongitude(32.748870);
        GPSImageNode south = new GPSImageNode("south", "South.png", southL, 90, true);
        gpsWorldHandler.addGPSObjectCumilative(south);
        south.scaleByUniform(0.05f);
        south.setVisible(true);


        //West Node
        Location westL = new Location("dummyprovider");
        westL.setLatitude(39.864003);
        westL.setLongitude(32.744766);
        GPSImageNode west = new GPSImageNode("west", "West.png", westL, 180, true);
        gpsWorldHandler.addGPSObjectCumilative(west);
        west.scaleByUniform(0.05f);
        west.setVisible(true);



        //East Node
        Location eastL = new Location("dummyprovider");
        eastL.setLatitude(39.863903);
        eastL.setLongitude(32.751890);
        GPSImageNode east = new GPSImageNode("east", "East.png", eastL, 0, true);
        gpsWorldHandler.addGPSObjectCumilative(east);
        east.scaleByUniform(0.05f);
        east.setVisible(true);

    }

    //Testing

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d("AR_VIEW_ACTIVITY", "Tapped");
        //Useless, for testing only.
        //GPSManager.interpolateMotionUsingHeading = ! GPSManager.interpolateMotionUsingHeading;
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        String id = gpsWorldHandler.getFocusedGPSObject().getID();
        Log.d("TOUCH_EVENT", id);

        //For testing purposes, when tapped, only the focused node will be shown.
        //gpsWorldHandler.showOnly(id);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA_AND_FINE_LOCATION) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Permissions granted!
                //We are recreating here because onCreate() method of ARActivity class checks for camera permission.
                recreate();
            } else {
                //permission not granted close the activity.
                Toast.makeText(getApplicationContext(), "Camera and Location permissions are needed for AR functionalities!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}