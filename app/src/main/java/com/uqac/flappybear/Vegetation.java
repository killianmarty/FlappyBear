package com.uqac.flappybear;

class Vegetation extends Sprite{
    
    static SpriteAttributes select(){
        double w;
        double h;
        int texture;
        switch((int)(Math.random()*3)){
            case 0:
                w=17;
                h=29;
                texture = R.drawable.tree1;
                break;
            case 1:
                w=18;
                h=30;
                texture = R.drawable.tree2;
                break;
            case 2:
                w=25;
                h=30;
                texture = R.drawable.tree3;
                break;
            default:
                w = 0;
                h = 0;
                texture = 0;
                break;
        }
        return new SpriteAttributes(w, h, texture);
    }

    public Vegetation(double x, double y){
        super(x, y, 0, 0);
        
        SpriteAttributes attributes = Vegetation.select();
        this.w = attributes.w;
        this.h = attributes.h;
        
        this.addTexture(attributes.texture);

        Sprite.pushSprite(this);
    }
    
}