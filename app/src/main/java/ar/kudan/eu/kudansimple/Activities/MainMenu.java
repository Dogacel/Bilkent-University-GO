package ar.kudan.eu.kudansimple.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.R;

public class MainMenu extends AppCompatActivity {
    public static final String IS_SOURCE = "MainMenu is the source for this intent";

    private Intent mapIntent, arIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openList( View v) { //linked to button_give_directions
        Intent intent = new Intent( this, BuildingList.class);
        intent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, BuildingList.IS_SOURCE);
        startActivity( intent);
    }

    public void openMap( View v) { //linked to button_show_location
        mapIntent = new Intent( this, MapActivity.class);
        mapIntent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, MapActivity.IS_SOURCE);
        startActivity( mapIntent);
    }

    public void openAR( View v) { //linked to button_identify_buildings
        arIntent = new Intent( this, ARViewActivity.class);
        arIntent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, ARViewActivity.IS_SOURCE);
        startActivity( arIntent);
    }
}
