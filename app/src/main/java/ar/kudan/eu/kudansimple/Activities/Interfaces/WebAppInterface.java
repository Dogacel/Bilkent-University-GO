package ar.kudan.eu.kudansimple.Activities.Interfaces;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import ar.kudan.eu.kudansimple.Activities.BuildingInfo;
import ar.kudan.eu.kudansimple.Activities.MapActivity;
import ar.kudan.eu.kudansimple.Constants;

/**
 * Created by Mehmet Ata YURTSEVER on 31.03.2018.
 */

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    public void goPanel(String building){
        Intent intent = new Intent( mContext, BuildingInfo.class);
        intent.putExtra( Constants.EXTRA_MESSAGE_BUILDING, buildingNameToNumber( building));
        intent.putExtra( Constants.EXTRA_MESSAGE_SOURCE, MapActivity.IS_SOURCE);
    }

    public int buildingNameToNumber( String name){ // the map provides the building name, the info panel uses the building number,
                                                    //this method patches things up
        for ( int i = 0; i < Constants.infoStrings.length; i++) {
            if( Constants.infoStrings[i][0] == name) { // compares the name string for each building with name
                return i;
            }
        }
        return -1;
    }



}
