package ar.kudan.eu.kudansimple;

/*
 * This class is used to store the information and constants needed for the Building Information Screen
 */
public abstract class Constants {
    public static final String EXTRA_MESSAGE_BUILDING = "com.teamazm.bugo.LIST_MESSAGE"; //the key that is used to determine the building for the info screen
    public static final String EXTRA_MESSAGE_SOURCE = "com.teamazm.bugo.SOURCE"; //the key that is used to determine the source for the info screen

    public static final int NUM_BUILDINGS = 3;
    public static final int A_BUILDING = 0;
    public static final int B_BUILDING = 1;
    public static final int FF_BUILDING = 2;


    public static final int[][] imageIDs = new int[][]{ // buildings have to be in alphabetical order in here and the
                                                            //building list to keep the program consistent & working
            new int[]{ R.drawable.a_building, R.drawable.a_first_floor, R.drawable.a_second_floor}, // A Building
            new int[]{ R.drawable.blank_photo}, // B Building
            new int[]{ R.drawable.ff_building, R.drawable.ff_first_floor}, // FF Building
    };

    public static final String[][] infoStrings = new String[][]{ // building name , departments, num_floors, free labs
            new String[]{ "A Building", "not specified","not specified","not specified"},
            new String[]{ "B Building", "Law, BCC", "3", "3rd and basement floor"},
            new String[1]
    };

}
