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
        if(sprite instanceof Player) {
            ((Player)sprite).landed = (((Player)sprite).y <= 0 && ((Player)sprite).vy > Settings.GROUND_CRASH_SEIL);
        }
    }

    public void uncollide(Sprite sprite){
        if(sprite instanceof Player) {
            ((Player)sprite).landed = false;
        }
    }
}