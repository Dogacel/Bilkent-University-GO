package ar.kudan.eu.kudansimple;


import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import android.webkit.JavascriptInterface;


public class MapActivity extends AppCompatActivity {
    public static final String IS_SOURCE = "MapActivity is the source for the event"; // important for the information screen


    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gps;

    WebView myView;

    private boolean loaded = false;
    private static MapActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //View mContentView;
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_map);

        //Button button = findViewById(R.id.button);
        myView = (WebView) findViewById(R.id.webview);
        myView.loadUrl("file:///android_asset/map.html");
        WebSettings myViewSettings = myView.getSettings();
        myViewSettings.setJavaScriptEnabled(true);
        loaded = true;


        myView.addJavascriptInterface(new WebAppInterface(this), "Android");

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


    public void updateLoc(){
        if (loaded) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            myView.loadUrl("javascript:updateFromAndroid("+ longitude +","+ latitude +")");
        }
    }

    static public void init(){
        instance = new MapActivity();
    }

    static public MapActivity getInstance(){
        return instance;
    }

}
