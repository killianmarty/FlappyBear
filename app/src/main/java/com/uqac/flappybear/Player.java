package com.uqac.flappybear;

public class Player extends MovingSprite{
    double fuel;
    Boolean throttle;
    Boolean landed;
    int score;

    public Player(double x, double y, double w, double h, double vx, double vy){
        super(x, y, w, h, vx, vy);
        this.fuel = 100;
        this.throttle = false;
        this.landed = false;
        this.score = 0;

        this.addTexture(R.drawable.plane);

        //this.initAudio();
    }
}
