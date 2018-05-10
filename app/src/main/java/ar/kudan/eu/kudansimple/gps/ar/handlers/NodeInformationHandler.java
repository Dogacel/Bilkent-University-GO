package ar.kudan.eu.kudansimple.gps.ar.handlers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import ar.kudan.eu.kudansimple.R;
import ar.kudan.eu.kudansimple.gps.information.NodeARInformation;

/**
 * Retrieves ARNode information from json file.
 */

public class NodeInformationHandler {

    private static int BUFFER_SIZE = 1024;
    private static String TAG = "NODE_INFO";

    /**
     * gets JSON string from resources.
     *
     * @param context Desired context.
     */
    private static String getJSONString(Context context) {
        InputStream is;
        Writer writer;
        Reader reader;
        char[] buffer;
        int n;

        is = context.getResources().openRawResource(R.raw.map);
        buffer = new char[BUFFER_SIZE];
        reader = new BufferedReader(new InputStreamReader(is));
        writer = new StringWriter();

        try {
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOExeption while reading map.geojson", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOExeption while closing inputStream.", e);
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    /**
     * returns NodeARInformation of every building in map.geojson file.
     *
     * @param c required to read from res folder.
     * @return an ArrayList of ARNodeInformation containing every building.
     */
    public static ArrayList<NodeARInformation> getAllNodeARInformation(Context c) {
        JSONObject reader;
        ArrayList<NodeARInformation> output;
        JSONArray buildings;
        JSONObject building;

        output = new ArrayList<>();

        try {
            reader = new JSONObject(getJSONString(c));
            buildings = reader.getJSONArray("features");

            for (int i = 0; i < buildings.length(); i++) {
                building = buildings.getJSONObject(i);

                output.add(createNodeARInformation(building));
            }

        } catch (JSONException e) {
            Log.e(TAG, "JSONException something went wrong while reading map.geojson (consult Faruk, I am aware that this is a terrible error message)", e);
            e.printStackTrace();
        }

        return output;

    }

    /**
     * Constructs a NodeARInformation object.
     *
     * @param building building
     * @return information
     * @throws JSONException exception
     */
    private static NodeARInformation createNodeARInformation(JSONObject building) throws JSONException {
        float[] position;
        position = getPosition(building);

        return new NodeARInformation(building.getJSONObject("properties").getString("name"),
                position[1], position[0]);
    }

    /**
     * calculates the middle point of the building.
     *
     * @param building building
     * @return position
     * @throws JSONException exception
     */
    private static float[] getPosition(JSONObject building) throws JSONException {
        float[] output = {0f, 0f}; // output[0] is the x position and output[1] is the y position.
        JSONArray pointArray;

        float noOfPoints;

        pointArray = building.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
        noOfPoints = pointArray.length();

        for (int i = 0; i < noOfPoints; i++) {
            output[0] = output[0] + (float) pointArray.getJSONArray(i).getDouble(0);
            output[1] = output[1] + (float) pointArray.getJSONArray(i).getDouble(1);
        }

        output[0] /= noOfPoints;
        output[1] /= noOfPoints;

        return output;

    }

}
