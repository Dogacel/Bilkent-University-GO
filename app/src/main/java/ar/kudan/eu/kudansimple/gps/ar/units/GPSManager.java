package ar.kudan.eu.kudansimple.gps.ar.units;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import ar.kudan.eu.kudansimple.gps.location.PlayLocationListener;
import ar.kudan.eu.kudansimple.gps.location.PlayLocationManager;
import ar.kudan.eu.kudansimple.sensor.Bearing;
import ar.kudan.eu.kudansimple.sensor.tools.Compass;
import eu.kudan.kudan.ARGyroManager;
import eu.kudan.kudan.ARNode;
import eu.kudan.kudan.ARRenderer;
import eu.kudan.kudan.ARRendererListener;
import eu.kudan.kudan.ARWorld;

/**
 * GPSManager class for handling GPSNodes on an ARWorld.
 */
public class GPSManager implements PlayLocationListener, ARRendererListener {

    static boolean interpolateMotionUsingHeading; //Testing
    static Vector3f northVector;
    private static Bearing bearingNorth;

    private Compass compass;
    private ARWorld arWorld; //Current world.
    private PlayLocationManager playLocationManager;
    private Location previousLocation; //Last location retrieved.


    /**
     * Constructor for GPSManager
     *
     * @param world    ARWorld for displaying GPSNode objects.
     * @param activity Current activity for getting location service.
     */
    public GPSManager(ARWorld world, Activity activity) {

        this.arWorld = world;

        this.previousLocation = null;
        northVector = null;

        ARGyroManager gyroManager = ARGyroManager.getInstance(); //Init gyroManager.
        gyroManager.initialise();

        this.arWorld.setVisible(false);
        interpolateMotionUsingHeading = false;

        bearingNorth = new Bearing();
        compass = new Compass(activity, bearingNorth);

        if (playLocationManager == null)
            playLocationManager = new PlayLocationManager(activity, this);
    }

    /**
     * Gets angle between two position vectors.
     *
     * @param source      Source location
     * @param destination Destination location
     * @return Angle between two locations in degrees.
     */
    public static float bearingFrom(Location source, Location destination) {
        return source.bearingTo(destination);
    }

    /**
     * Gets bearing to north.
     *
     * @return bearing to north in degrees.
     */
    public static float getRealBearing() {
        return bearingNorth.getDegrees();
    }

    /**
     * Destroys the manager.
     */
    public void destroy() {
        compass.destroy();
        playLocationManager.stop();
    }

    /**
     * Starts the GPSManager
     */
    public void start() {
        playLocationManager.start();
        this.arWorld.setVisible(true);

        ARGyroManager.getInstance().start();
        ARRenderer.getInstance().addListener(this);
    }

    /**
     * Get current location
     *
     * @return previous location retrieved.
     */
    private Location getCurrentLocation() {
        return previousLocation;
    }

    /**
     * Parses the location update when the location is updated.
     *
     * @param l new location
     */
    @Override
    public void parseUpdate(Location l) {

        this.previousLocation = l;

        if (getCurrentLocation() != null) {
            if (GPSManager.northVector == null) {
                this.calculateNorthVector();
            }
            for (ARNode node : this.getArWorld().getChildren()) { //Update each children's Position according to the new location data.
                if (node instanceof GPSImageNode) {
                    GPSImageNode gpsImageNode = (GPSImageNode) node;
                    gpsImageNode.updateWorldPosition(previousLocation);
                }
            }
            Log.d("GPS_DEBUG", previousLocation.toString());
        }

    }

    /**
     * returns bearing to north actively.
     *
     * @return bearing to north in degrees.
     */
    public float getBearingToNorth() {
        return compass.getCurrentBearing();
    }

    /**
     * Calculates the north vector according to ARWorld position, device position and real north.
     */
    private void calculateNorthVector() {

        float bearing = bearingNorth.getDegrees();

        //Continue to use compass for focused click
        //compass.destroy();

        Log.d("GPS_DEBUG", "Bearing : " + bearing);

        Quaternion qt = new Quaternion();
        northVector = qt.fromAngleAxis((float) Math.toRadians(180 + bearing), new Vector3f(0, -1, 0)).mult(new Vector3f(-1, 0, 0));

        Log.d("GPS_DEBUG", "North vector : " + northVector);
    }

    /**
     * Updates the World orientation according to GyroManager's orientation.
     */
    private void updateNode() {
        this.arWorld.setOrientation(ARGyroManager.getInstance().getWorld().getOrientation());
    }

    /**
     * Returns current ARWorld.
     *
     * @return current ARWorld.
     */
    public ARWorld getArWorld() {
        return this.arWorld;
    }

    /**
     * Sets current ARWorld to world
     *
     * @param world Current World.
     * @return success.
     */
    public boolean setArWorld(ARWorld world) {
        if (world == null) {
            return false;
        }
        this.arWorld = world;
        return true;
    }

    @Override
    public void preRender() {
        this.updateNode();
    }

    @Override
    public void postRender() {

    }

    @Override
    public void rendererDidPause() {

    }

    @Override
    public void rendererDidResume() {

    }

}
