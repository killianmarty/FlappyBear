package com.uqac.flappybear;

class Mountain extends Sprite{

    static double lastRightBoundX = 0;

    static SpriteAttributes select(){
        double w;
        double h;
        int texture;
        switch((int)(Math.random()*5) + 1){
            case 1:
                w=384;
                h=100;
                texture = R.drawable.mountain1;
                break;
            case 2:
                w=265;
                h=114;
                texture = R.drawable.mountain2;
                break;
            case 3:
                w=384;
                h=130;
                texture = R.drawable.mountain3;
                break;
            case 4:
                w=192;
                h=192;
                texture = R.drawable.mountain4;
                break;
            case 5:
                w=386;
                h=100;
                texture = R.drawable.mountain5;
                break;
            default:
                w = 0;
                h = 0;
                texture = 0;
                break;
        }
        return new SpriteAttributes(w, h, texture);
    }

    public Mountain(double x, double y){
        super(x, y, 0, 0);
        SpriteAttributes attributes = Mountain.select();
        this.w = attributes.w;
        this.h = attributes.h;
        
        Mountain.lastRightBoundX = this.getRightBoundX();

        this.addTexture(attributes.texture);

        Sprite.pushBackgroundSprite(this);
    }
    
}