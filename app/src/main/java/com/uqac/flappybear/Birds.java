package com.uqac.flappybear;

class Birds extends MovingSprite{

    static double lastRightBoundX = 0;

    public Birds(double x, double y){
        super(x, y, 40, 30, -10, 0);
        Birds.lastRightBoundX = this.getRightBoundX();

        this.addTexture(R.drawable.birds1);
        this.addTexture(R.drawable.birds2);

        this.textureChangeFrequency = 8;

        Sprite.pushBackgroundSprite(this);
    }
    
}