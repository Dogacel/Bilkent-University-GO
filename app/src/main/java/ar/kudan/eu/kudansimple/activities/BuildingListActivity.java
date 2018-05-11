package ar.kudan.eu.kudansimple.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.vision.text.Line;

import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.R;

public class BuildingListActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String IS_SOURCE = "BuildingListActivity is the source for this intent";
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        LinearLayout layout = (LinearLayout) findViewById(R.id.list);


        buttons = new Button[Constants.NUM_BUILDINGS];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(this);
            buttons[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            buttons[i].setText(Constants.infoStrings[i][0]);
            buttons[i].setId(i);
            buttons[i].setOnClickListener(this);
            layout.addView(buttons[i]);
        }

    }

    public void buttonClicked(View b) {
        for (int i = 0; i < buttons.length; i++) {
            if (b == buttons[i]) {
                openInfoScreen(i);
            }
        }

    }


    private void openInfoScreen(int building) {
        Intent intent = new Intent(this, BuildingInfoActivity.class);

        intent.putExtra(Constants.EXTRA_MESSAGE_BUILDING, building);
        intent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, IS_SOURCE);
        startActivity(intent);


    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < buttons.length; i++) {
            if (view == buttons[i]) {
                openInfoScreen(i);
            }
        }
    }
}
