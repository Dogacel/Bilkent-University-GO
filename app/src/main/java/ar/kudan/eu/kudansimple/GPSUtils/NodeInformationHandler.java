package ar.kudan.eu.kudansimple.GPSUtils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import ar.kudan.eu.kudansimple.R;

/**
 * Retrieves ARNode information from json file.
 */

public class NodeInformationHandler {

    private static int BUFFER_SIZE = 1024;
    private static String TAG = "NODE_INFO";

    /**
     * gets JSON string from resources.
     * @param context Desired context.
     */
    private String getJSONString( Context context) {
        InputStream is;
        Writer writer;
        Reader reader;
        char[] buffer;
        int n;

        is = context.getResources().openRawResource(R.raw.map);
        buffer = new char[BUFFER_SIZE];
        reader = new BufferedReader(new InputStreamReader( is));
        writer = new StringWriter();

        try{
            while ( ( n = reader.read( buffer)) != -1 ) {
                writer.write(buffer, 0, n);
            }
        } catch ( IOException e) {
            Log.e( TAG, "IOExeption while reading map.geojson", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

}
