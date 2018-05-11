package ar.kudan.eu.kudansimple;


import android.app.Application;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;
import ar.kudan.eu.kudansimple.gps.ar.handlers.NodeInformationHandler;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;
import ar.kudan.eu.kudansimple.gps.information.NodeARInformation;
import ar.kudan.eu.kudansimple.sensor.tools.Compass;

/**
 * App class for executing tasks while app is starting.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContainerManager.initialise();

        Compass.init(this.getApplicationContext());


        //ContainerManager cm = ContainerManager.getInstance();

        GPSWorldHandler gpsWorldHandler = ContainerManager.getInstance().getGpsWorldHandler();

        /* TODO: Initialize objects around
        *  Currently manual.
        *  NodeInformationHandler must initialise the nodes with the same protocol.
        * */

        ArrayList<NodeARInformation> arr = NodeInformationHandler.getAllNodeARInformation(getBaseContext());


        for (NodeARInformation inf : arr) {
            Location loc = new Location("dummyprovider");
            loc.setLatitude(inf.getLat());
            loc.setLongitude(inf.getLon());
            GPSImageTemplate temp = new GPSImageTemplate(inf.getLabel(), "labels/" + inf.getLabel() + ".png", loc, 20, 0, true);
            gpsWorldHandler.dumpTemplates(temp);
            temp.scaleByUniform(0.02f);
            temp.show(true);
            Log.d("TEST_NODES", inf.toString());

        }


    }
}


/* Data for initializing tests for GPSWorldHandler

    //link to auto-generate marker : https://hastebin.com/uxumovuhal.java
*/