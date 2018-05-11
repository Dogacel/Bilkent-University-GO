package ar.kudan.eu.kudansimple.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.ContainerManager;
import ar.kudan.eu.kudansimple.R;
import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;

public class MainMenuActivity extends AppCompatActivity {
    public static final String IS_SOURCE = "MainMenuActivity is the source for this intent";

    private Intent mapIntent, arIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openList(View v) { //linked to the give me directions button
        Intent intent = new Intent(this, BuildingListActivity.class);
        intent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, BuildingListActivity.IS_SOURCE);
        startActivity(intent);
    }

    public void openMap(View v) { //linked to the show me my location button
        mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, MapActivity.IS_SOURCE);
        startActivity(mapIntent);
    }

    public void openAR(View v) { //linked to the identify buildings button
        GPSWorldHandler gpsWorldHandler = ContainerManager.getInstance().getGpsWorldHandler();
        gpsWorldHandler.showAll();

        arIntent = new Intent(this, ARViewActivity.class);
        arIntent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, ARViewActivity.IS_SOURCE);
        startActivity(arIntent);
    }

    public void showCredits(View view) { //linked to the credits button
        Intent intent = new Intent(this, Credits.class);
        startActivity(intent);
    }
}
