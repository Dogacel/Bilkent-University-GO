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
            new int[]{R.drawable.a_building, R.drawable.a_first_floor, R.drawable.a_second_floor}, // A Building
            new int[]{R.drawable.blank_photo}, // B Building
            new int[]{R.drawable.blank_photo}, // CAFEIN
            new int[]{R.drawable.blank_photo}, // Dorm 50
            new int[]{R.drawable.blank_photo}, // Dorm 51
            new int[]{R.drawable.blank_photo}, // Dorm 52
            new int[]{R.drawable.blank_photo}, // Dorm 54
            new int[]{R.drawable.blank_photo}, // Dorm 55
            new int[]{R.drawable.blank_photo}, // Dorm 60
            new int[]{R.drawable.blank_photo}, // Dorm 61
            new int[]{R.drawable.blank_photo}, // Dorm 62
            new int[]{R.drawable.blank_photo}, // Dorm 63
            new int[]{R.drawable.blank_photo}, // Dorm 64
            new int[]{R.drawable.blank_photo}, // Dorm 69
            new int[]{R.drawable.blank_photo}, // Dorm 70
            new int[]{R.drawable.blank_photo}, // Dorm 71
            new int[]{R.drawable.blank_photo}, // Dorm 72
            new int[]{R.drawable.blank_photo}, // Dorm 73
            new int[]{R.drawable.blank_photo}, // Dorm 74
            new int[]{R.drawable.blank_photo}, // Dorm 75
            new int[]{R.drawable.blank_photo}, // Dorm 76
            new int[]{R.drawable.blank_photo}, // Dorm 77
            new int[]{R.drawable.blank_photo}, // Dorm 78
            new int[]{R.drawable.blank_photo}, // Dormitory GYM
            new int[]{R.drawable.blank_photo}, // EA
            new int[]{R.drawable.blank_photo}, // EE
            new int[]{R.drawable.blank_photo}, // FA / FC
            new int[]{R.drawable.blank_photo}, // FB
            new int[]{R.drawable.blank_photo}, // FF
            new int[]{R.drawable.blank_photo}, // FOOD COURT
            new int[]{R.drawable.blank_photo}, // G
            new int[]{R.drawable.blank_photo}, // L
            new int[]{R.drawable.blank_photo}, // Libarry
            new int[]{R.drawable.blank_photo}, // Main GYM
            new int[]{R.drawable.blank_photo}, // Marmara
            new int[]{R.drawable.blank_photo}, // Mayfest
            new int[]{R.drawable.blank_photo}, // Meteksan
            new int[]{R.drawable.blank_photo}, // Nanotam
            new int[]{R.drawable.blank_photo}, // Prayer
            new int[]{R.drawable.blank_photo}, // SA
            new int[]{R.drawable.blank_photo}, // SB
            new int[]{R.drawable.blank_photo}, // Student Council
            new int[]{R.drawable.blank_photo}, // UNAM
            new int[]{R.drawable.blank_photo}, // V

    };

    public static final String[][] infoStrings = new String[][]{ // building name , departments, num_floors, free labs
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // A Building
            new String[]{"B Building", "not specified", "not specified", "not specified"}, // B Building
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // CAFEIN
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 50
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 51
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 52
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 54
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 55
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 60
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 61
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 62
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 63
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 64
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 69
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 70
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 71
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 72
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 73
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 74
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 75
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 76
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 77
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dorm 78
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Dormitory GYM
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // EA
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // EE
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // FA / FC
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // FB
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // FF
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // FOOD COURT
            new String[]{"G Building", "not specified", "not specified", "not specified"}, // G
            new String[]{"L Building", "not specified", "not specified", "not specified"}, // L
            new String[]{"Library", "not specified", "not specified", "not specified"},    // Library
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Main GYM
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Marmara
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Mayfest
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Meteksan
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Nanotam
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Prayer
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // SA
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // SB
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // Student Council
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // UNAM
            new String[]{"A Building", "not specified", "not specified", "not specified"}, // V
    };

}
