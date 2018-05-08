package ar.kudan.eu.kudansimple.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import ar.kudan.eu.kudansimple.Activities.BuildingInfo;
import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.R;

public class BuildingList extends AppCompatActivity {

    public static final String IS_SOURCE = "BuildingList is the source for this intent";
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);


        buttons = new Button[Constants.NUM_BUILDINGS];
        for( int i = 0; i < buttons.length; i ++) {
            buttons[i] = (Button)((LinearLayout)findViewById(R.id.list)).getChildAt(i);
        }

    }
    public void buttonClicked( View b) {
        for( int i = 0; i < buttons.length; i ++) {
            if ( b == buttons[i]) {
                openInfoScreen(i);
            }
        }

    }


    public void openInfoScreen(int building){
        Intent intent = new Intent(this, BuildingInfo.class);
        intent.putExtra( Constants.EXTRA_MESSAGE_BUILDING, building);
        intent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, IS_SOURCE);
        startActivity( intent);
    }

}
