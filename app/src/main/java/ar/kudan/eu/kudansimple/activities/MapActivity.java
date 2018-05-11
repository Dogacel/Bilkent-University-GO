package ar.kudan.eu.kudansimple.activities;


import android.Manifest;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import ar.kudan.eu.kudansimple.ContainerManager;
import ar.kudan.eu.kudansimple.gps.location.PlayLocationListener;
import ar.kudan.eu.kudansimple.gps.location.PlayLocationManager;
import ar.kudan.eu.kudansimple.R;
import ar.kudan.eu.kudansimple.activities.interfaces.WebAppInterface;


public class MapActivity extends AppCompatActivity {
    public static final String IS_SOURCE = "MapActivity is the source for the event"; // important for the information screen


    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private PlayLocationManager plm;
    private WebView myView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        plm.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //View mContentView;
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_map);

        //Button button = findViewById(R.id.button);
        myView = findViewById(R.id.webview);
        myView.clearCache(true);
        String URL = "file:///android_asset/map/map.html";
        //URL = "http://ata.yurtsever.ug.bilkent.edu.tr/";

        myView.loadUrl(URL);
        WebSettings myViewSettings = myView.getSettings();

        myViewSettings.setJavaScriptEnabled(true);
        myViewSettings.setAllowUniversalAccessFromFileURLs(true);

        ContainerManager.getInstance().setMapPlayLocationManager(plm);

        plm = new PlayLocationManager(this, new MapLocationListener());
        plm.start();

        WebAppInterface wai = new WebAppInterface(this);
        myView.addJavascriptInterface(wai, "Android");

        //mContentView = findViewById(R.id.fullscreen_content);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



//
//
//        button.setOnClickListener(new View.OnClickListener() {
//        @Override
//            public void onClick(View view) {
//                gps = new GPSTracker(MapActivity.this);
//
//                // check if GPS enabled
//                if(gps.canGetLocation()){
//
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//
//                    /
//                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
//                    //       + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//                    myView.loadUrl("javascript:updateFromAndroid("+ longitude +","+ latitude +")");
//
//                }else{
//                    // can't get location
//                    // GPS or Network is not enabled
//                    // Ask user to enable GPS/network in settings
//                    gps.showSettingsAlert();
//                }
//
//
//
//            }
//        });
//


    }

    public void createToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    private class MapLocationListener implements PlayLocationListener {
        public void parseUpdate(Location l) {

            double latitude = l.getLatitude();
            double longitude = l.getLongitude();
            myView.loadUrl("javascript:updateFromAndroid("+ longitude +","+ latitude +")");
            Log.d("GPS_DEBUG" , l.toString());
        }
    }


}
