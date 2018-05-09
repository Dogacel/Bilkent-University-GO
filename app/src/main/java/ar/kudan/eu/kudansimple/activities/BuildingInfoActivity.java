package ar.kudan.eu.kudansimple.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.kudan.eu.kudansimple.Constants;
import ar.kudan.eu.kudansimple.ContainerManager;
import ar.kudan.eu.kudansimple.R;
import ar.kudan.eu.kudansimple.gps.ar.handlers.GPSWorldHandler;

public class BuildingInfoActivity extends AppCompatActivity {
    public static final String IS_SOURCE = "Building info is the source for this intent";

    private int[] images;
    private String[] infoTexts;
    private int building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_info);

        Intent intent = getIntent();
        building = intent.getIntExtra(Constants.EXTRA_MESSAGE_BUILDING, 0);//not sure about the default value
        images = Constants.imageIDs[building];
        infoTexts = Constants.infoStrings[building];
        //setting the info texts
        if (infoTexts.length == 4) { // to prevent any errors to be caused by lack of information
            ((TextView) findViewById(R.id.building_name)).setText(infoTexts[0]);
            ((TextView) findViewById(R.id.departments_info)).setText(infoTexts[1]);
            ((TextView) findViewById(R.id.num_floors_info)).setText(infoTexts[2]);
            ((TextView) findViewById(R.id.free_labs_info)).setText(infoTexts[3]);
        }

        if (intent.getStringExtra(Constants.EXTRA_MESSAGE_SOURCE).equals(ARViewActivity.IS_SOURCE)) {
            findViewById(R.id.take_there).setVisibility(View.GONE);
        }

        ViewPager infoPictures = findViewById(R.id.infoPictures);
        infoPictures.setAdapter(new CustomPagerAdapter(this));


    }

    public void takeMeThere(View v) { // linked to take_there (Button)

        GPSWorldHandler gpsWorldHandler = ContainerManager.getInstance().getGpsWorldHandler();
        String ID = "north";
        if (building == 1) {
            ID = "east";
        } else if (building == 2) {
            ID = "south";
        } else if (building == 3) {
            ID = "west";
        }
        gpsWorldHandler.showOnly(ID);
        Log.d("TEST_CLICK", ID);

        Intent intent = new Intent(this, ARViewActivity.class);
        intent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, IS_SOURCE);
        intent.putExtra(Constants.EXTRA_MESSAGE_BUILDING, building);
        startActivity(intent);
    }

    public class CustomPagerAdapter extends PagerAdapter { //This class is taken directly from http://codetheory.in/android-image-slideshow-using-viewpager-pageradapter/


        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = itemView.findViewById(R.id.imageView);
            imageView.setImageResource(images[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}

