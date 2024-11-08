package com.uqac.flappybear;

public class Settings {
        //GLOBAL SETTINGS
    public static final int MAX_HEIGHT = 200;
    public static final int MAX_WIDTH = 600;
    public static final int GROUND_HEIGHT = 30;

    //PLAYER SETTINGS
    public static final int GRAVITY = 60;
    public static final double MAX_UP_ROTATION = Math.PI/3.5;
    public static final int GROUND_CRASH_SEIL = -50;
    public static final double SCORE_PER_PIXEL = 0.2;
    public static final int FUEL_PERCENT_PER_SECOND = 2;
    public static final int REFUEL_PERCENT_PER_SECOND = 20;

    //SPRITE GENERATION SETTINGS
    public static final double MIN_GENERATION_FREQUENCY = 0.1;
    public static final double MAX_GENERATION_FREQUENCY = 1;
    public static final double GENERATION_FREQUENCY_GAP = 0.03;
    public static final int MIN_VEHICLE_GENERATION_HEIGHT = MAX_HEIGHT * 2/3;
    public static final int VEHICLE_GENERATION_HEIGHT_GAP = MAX_HEIGHT * 1/4;

    //RENDERER SETTINGS
    public static final int PLAYER_DISPLAY_X = 20;
}
