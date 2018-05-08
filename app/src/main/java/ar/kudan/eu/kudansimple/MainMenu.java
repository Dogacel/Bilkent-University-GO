package ar.kudan.eu.kudansimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {
    public static final String IS_SOURCE = "MainMenu is the source for this intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openList( View v) { //linked to button_give_directions
        Intent intent = new Intent( this, BuildingList.class);
        intent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, IS_SOURCE);
        startActivity( intent);
    }

    public void openMap( View v) { //linked to button_show_location
        Intent intent = new Intent( this, MapActivity.class);
        intent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, IS_SOURCE);
        startActivity( intent);
    }

    public void openAR( View v) { //linked to button_identify_buildings
        Intent intent = new Intent( this, ARViewActivity.class);
        intent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, IS_SOURCE);
        startActivity( intent);
    }
}
