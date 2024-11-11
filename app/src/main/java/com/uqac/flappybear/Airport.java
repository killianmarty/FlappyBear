package com.uqac.flappybear;

class Airport extends Sprite{

    static double lastRightBoundX = 0;

    public Airport(double x, double y){
        super(x, y, 490, 112);
        Airport.lastRightBoundX = this.getRightBoundX();
        
        this.addTexture(R.drawable.airport);

        Sprite.pushSprite(this);
    }

    public void collide(Sprite sprite){
        sprite.landed = (sprite.y <= 0 && sprite.vy > GROUND_CRASH_SEIL);
    }

    public void uncollide(Sprite sprite){
        sprite.landed = false;
    }
}