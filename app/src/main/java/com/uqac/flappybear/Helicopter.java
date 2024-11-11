package com.uqac.flappybear;

class Helicopter extends MovingSprite{

    public Helicopter(double x, double y){
        super(x, y, 56, 20, -30, 0);

        this.addTexture(R.drawable.helicopter1);
        this.addTexture(R.drawable.helicopter2);

        this.textureChangeFrequency = 15;

        Sprite.pushSprite(this);
    }
    
}