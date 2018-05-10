package ar.kudan.eu.kudansimple;


import android.app.Application;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;
import ar.kudan.eu.kudansimple.gps.ar.handlers.NodeInformationHandler;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;
import ar.kudan.eu.kudansimple.gps.information.NodeARInformation;

/**
 * App class for executing tasks while app is starting.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContainerManager.initialise();

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
            GPSImageTemplate temp = new GPSImageTemplate(inf.getLabel(), "labels/" + inf.getLabel() + ".png", loc, 5, 0, true);
            gpsWorldHandler.dumpTemplates(temp);
            temp.scaleByUniform(0.02f);
            temp.show(true);
            Log.d("TEST_NODES", inf.toString());

        }

        //North Node
        Location northL = new Location("dummyprovider");
        northL.setLatitude(39.866699);
        northL.setLongitude(32.748784);
        GPSImageTemplate north = new GPSImageTemplate("north", "North.png", northL, 5, 0, true);
        gpsWorldHandler.dumpTemplates(north);
        north.scaleByUniform(0.05f);
        north.show(true);


        //South Node
        Location southL = new Location("dummyprovider");
        southL.setLatitude(39.861362);
        southL.setLongitude(32.748870);
        GPSImageTemplate south = new GPSImageTemplate("south", "South.png", southL, 5, 90, true);
        gpsWorldHandler.dumpTemplates(south);
        south.scaleByUniform(0.05f);
        south.show(true);


        //West Node
        Location westL = new Location("dummyprovider");
        westL.setLatitude(39.864003);
        westL.setLongitude(32.744766);
        GPSImageTemplate west = new GPSImageTemplate("west", "West.png", westL, 5, 180, true);
        gpsWorldHandler.dumpTemplates(west);
        west.scaleByUniform(0.05f);
        west.show(true);


        //East Node
        Location eastL = new Location("dummyprovider");
        eastL.setLatitude(39.863903);
        eastL.setLongitude(32.751890);
        GPSImageTemplate east = new GPSImageTemplate("east", "East.png", eastL, 5, 0, true);
        gpsWorldHandler.dumpTemplates(east);
        east.scaleByUniform(0.05f);
        east.show(true);


    }
}


/* Data for initializing tests for GPSWorldHandler

    //link to auto-generate marker : https://hastebin.com/uxumovuhal.java
*/