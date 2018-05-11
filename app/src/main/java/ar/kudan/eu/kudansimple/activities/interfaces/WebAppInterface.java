package ar.kudan.eu.kudansimple.activities.interfaces;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import ar.kudan.eu.kudansimple.activities.BuildingInfoActivity;
import ar.kudan.eu.kudansimple.activities.MapActivity;
import ar.kudan.eu.kudansimple.Constants;

/**
 * Simple WebApp Interface
 */

public class WebAppInterface {
    private Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void goPanel(String building) {
        showToast(building);
        Intent intent = new Intent(mContext, BuildingInfoActivity.class);
        intent.putExtra(Constants.EXTRA_MESSAGE_BUILDING, Integer.parseInt(building));
        intent.putExtra(Constants.EXTRA_MESSAGE_SOURCE, MapActivity.IS_SOURCE);
        mContext.startActivity(intent);
    }


}
