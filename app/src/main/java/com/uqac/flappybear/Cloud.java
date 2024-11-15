package com.uqac.flappybear;

class Cloud extends Sprite{

    static double lastRightBoundX = 0;

    static SpriteAttributes select(){
        int texture;
        switch((int)(Math.random()*7)){
            case 0:
                texture = R.drawable.cloud1;
                break;
            case 1:
                texture = R.drawable.cloud2;
                break;
            case 2:
                texture = R.drawable.cloud3;
                break;
            case 3:
                texture = R.drawable.cloud4;
                break;
            case 4:
                texture = R.drawable.cloud4;
                break;
            case 5:
                texture = R.drawable.cloud5;
                break;
            case 6:
                texture = R.drawable.cloud6;
                break;
            case 7:
                texture = R.drawable.cloud7;
                break;
            default:
                texture = 0;
                break;
        }
        return new SpriteAttributes(40, 30, texture);
    }

    public Cloud(double x, double y){
        super(x, y, 0, 0);

        Cloud.lastRightBoundX = this.getRightBoundX();

        SpriteAttributes attributes = Cloud.select();
        this.w = attributes.getWidth();
        this.h = attributes.getHeight();

        this.addTexture(attributes.getTexture());

        Sprite.pushBackgroundSprite(this);
    }
    
}