package ar.kudan.eu.kudansimple;


import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;
import ar.kudan.eu.kudansimple.gps.location.PlayLocationManager;

/**
 * ContainerManager is for organising some structures across couple activities
 * Most importantly it has gpsWorldHandler and this is used to show certain buildings with certain actions.
 * Please refer to @{@link GPSWorldHandler} for further info.
 */
public class ContainerManager {

    private static ContainerManager instance;

    private PlayLocationManager arPlayLocationManager, mapPlayLocationManager;
    private GPSWorldHandler gpsWorldHandler;

    /**
     * Constructs a new ContainerManager object.
     */
    private ContainerManager() {
        gpsWorldHandler = new GPSWorldHandler(null);
    }

    /**
     * Initialise the instance.
     */
    public static void initialise() {
        instance = new ContainerManager();
    }

    /**
     * Get Instance of the container manager.
     *
     * @return the instance
     */
    public static ContainerManager getInstance() {
        if (instance == null)
            throw new NullPointerException("Instance of Container Manager is null !");
        return instance;
    }

    /**
     * Set the location manager for the ar.
     *
     * @param playLocationManager ar locationManager.
     */
    public void setArPlayLocationManager(PlayLocationManager playLocationManager) {
        this.arPlayLocationManager = playLocationManager;
    }

    /**
     * Get world handler
     *
     * @return world handler
     */
    public GPSWorldHandler getGpsWorldHandler() {
        return gpsWorldHandler;
    }

    /**
     * Set world handler for the container manager.
     *
     * @param gpsWorldHandler world handler.
     */
    public void setGpsWorldHandler(GPSWorldHandler gpsWorldHandler) {
        this.gpsWorldHandler = gpsWorldHandler;
    }

    /**
     * Get map location manager
     *
     * @return map location manager
     */
    public PlayLocationManager getMapPlayLocationManager() {
        return mapPlayLocationManager;
    }

    /**
     * Set the map location manager.
     *
     * @param playLocationManager map locationManager
     */
    public void setMapPlayLocationManager(PlayLocationManager playLocationManager) {
        this.mapPlayLocationManager = mapPlayLocationManager;
    }

    /**
     * Get ar location manager
     *
     * @return ar location manager
     */
    public PlayLocationManager getARLocationManager() {
        return arPlayLocationManager;
    }
}
