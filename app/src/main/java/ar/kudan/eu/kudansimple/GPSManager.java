package ar.kudan.eu.kudansimple;

import eu.kudan.kudan.ARWorld;

/**
 * Created by dogacel on 21.03.2018.
 */

public class GPSManager {
    private ARWorld arWorld;

    public GPSManager (ARWorld world) {
        this.arWorld = world;
    }

    public ARWorld getArWorld() {
        return this.arWorld;
    }

    public boolean setArWorld(ARWorld world) {
        if (world == null) {
            return false;
        }
        this.arWorld = world;
        return true;
    }
}
