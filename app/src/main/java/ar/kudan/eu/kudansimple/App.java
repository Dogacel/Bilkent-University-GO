package ar.kudan.eu.kudansimple;


import android.app.Application;
import android.location.Location;

import ar.kudan.eu.kudansimple.GPSUtils.GPSImageNode;
import ar.kudan.eu.kudansimple.GPSUtils.GPSWorldHandler;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContainerManager.initialise();

        ContainerManager cm = ContainerManager.getInstance();

    }
}


/* Data for initializing tests for GPSWorldHandler

        //North Node
        Location northL = new Location("dummyprovider");
        northL.setLatitude(39.866699);
        northL.setLongitude(32.748784);
        GPSImageNode north = new GPSImageNode("north", "North.png", northL,5, 0, true);
        gpsWorldHandler.addGPSObjectCumilative(north);
        north.scaleByUniform(0.05f);
        north.setVisible(true);


        //South Node
        Location southL = new Location("dummyprovider");
        southL.setLatitude(39.861362);
        southL.setLongitude(32.748870);
        GPSImageNode south = new GPSImageNode("south", "South.png", southL, 5,90, true);
        gpsWorldHandler.addGPSObjectCumilative(south);
        south.scaleByUniform(0.05f);
        south.setVisible(true);


        //West Node
        Location westL = new Location("dummyprovider");
        westL.setLatitude(39.864003);
        westL.setLongitude(32.744766);
        GPSImageNode west = new GPSImageNode("west", "West.png", westL, 5, 180, true);
        gpsWorldHandler.addGPSObjectCumilative(west);
        west.scaleByUniform(0.05f);
        west.setVisible(true);



        //East Node
        Location eastL = new Location("dummyprovider");
        eastL.setLatitude(39.863903);
        eastL.setLongitude(32.751890);
        GPSImageNode east = new GPSImageNode("east", "East.png", eastL, 5, 0, true);
        gpsWorldHandler.addGPSObjectCumilative(east);
        east.scaleByUniform(0.05f);
        east.setVisible(true);

    //link to auto-generate marker : https://hastebin.com/uxumovuhal.java
*/