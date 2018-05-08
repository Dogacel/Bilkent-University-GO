package ar.kudan.eu.kudansimple;


import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;
import ar.kudan.eu.kudansimple.gps.location.PlayLocationManager;

public class ContainerManager {

    private static ContainerManager instance;

    private PlayLocationManager arPlayLocationManager, mapPlayLocationManager;
    private GPSWorldHandler gpsWorldHandler;

    private ContainerManager() {
        gpsWorldHandler = new GPSWorldHandler(null);
    }

    public static void initialise() {
        instance = new ContainerManager();
    }

    public static ContainerManager getInstance() {
        if (instance == null)
            throw new NullPointerException("Instance of Container Manager is null !");
        return instance;
    }

    public void setGpsWorldHandler(GPSWorldHandler gpsWorldHandler) {
        this.gpsWorldHandler = gpsWorldHandler;
    }


    public void setArPlayLocationManager(PlayLocationManager playLocationManager) {
        this.arPlayLocationManager = playLocationManager;
    }

    public void setMapPlayLocationManager(PlayLocationManager playLocationManager) {
        this.mapPlayLocationManager = mapPlayLocationManager;
    }

    public GPSWorldHandler getGpsWorldHandler() {
        return gpsWorldHandler;
    }

    public PlayLocationManager getMapPlayLocationManager() {
        return mapPlayLocationManager;
    }

    public PlayLocationManager getARLocationmanager() {
        return arPlayLocationManager;
    }
}
