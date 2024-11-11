package com.uqac.flappybear;

class FighterJet extends MovingSprite{

    public FighterJet(double x, double y){
        super(x, y, 40, 21, -70, 0);
        
        this.addTexture(R.drawable.fighter_jet);

        Sprite.pushSprite(this);
    }

}