package ar.kudan.eu.kudansimple;


import android.app.Application;
import android.location.Location;

import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;
import ar.kudan.eu.kudansimple.gps.ar.units.GPSImageTemplate;

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

        //Rektorluk
        double lat = 39.871495;
        double lon = 32.749671;
        Location testL = new Location("dummyprovider");
        testL.setLatitude(lat);
        testL.setLongitude(lon);
        GPSImageTemplate test = new GPSImageTemplate("r", "r.png", testL, 15, 0, true);
        gpsWorldHandler.dumpTemplates(test);
        test.scaleByUniform(0.03f);
        test.show(true);

        //Yemekhane
        lat = 39.870578;
        lon = 32.750563;

        Location testL1 = new Location("dummyprovider");
        testL1.setLatitude(lat);
        testL1.setLongitude(lon);
        GPSImageTemplate test1 = new GPSImageTemplate("y", "y.png", testL1, 15, 0, true);
        gpsWorldHandler.dumpTemplates(test1);
        test1.scaleByUniform(0.03f);
        test1.show(true);


        //Cafeinn
        lat = 39.870002;
        lon = 32.750496;

        Location testL3 = new Location("dummyprovider");
        testL3.setLatitude(lat);
        testL3.setLongitude(lon);
        GPSImageTemplate test3 = new GPSImageTemplate("c", "c.png", testL3, 15, 0, true);
        gpsWorldHandler.dumpTemplates(test3);
        test3.scaleByUniform(0.03f);
        test3.show(true);


        //Kutuphane
        lat = 39.870316;
        lon = 32.749557;

        Location testL2 = new Location("dummyprovider");
        testL2.setLatitude(lat);
        testL2.setLongitude(lon);
        GPSImageTemplate test2 = new GPSImageTemplate("l", "l.png", testL2, 15, 0, true);
        gpsWorldHandler.dumpTemplates(test2);
        test2.scaleByUniform(0.03f);
        test2.show(true);

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