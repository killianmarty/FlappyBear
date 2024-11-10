package com.uqac.flappybear;

public class Baloon extends MovingSprite{

    public Baloon(double x, double y){
        super(x, y, 26, 40, -10, 0);
        addTexture(R.drawable.baloon);

        Sprite.pushSprite(this);
    }
}
