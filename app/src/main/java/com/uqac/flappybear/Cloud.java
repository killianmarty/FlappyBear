package com.uqac.flappybear;

class Cloud extends Sprite{

    static double lastRightBoundX = 0;

    public Cloud(double x, double y){
        super(x, y, 40, 30);
        Cloud.lastRightBoundX = this.getRightBoundX();

        let id = (Math.floor(Math.random()*7) + 1);
        this.addTexture(R.id.cloud1);
        //this.addTexture("assets/background/cloud" + id.toString() + ".png");

        Sprite.pushBackgroundSprite(this);
    }
    
}