package com.uqac.flappybear;

class Building extends Sprite{
    
    static SpriteAttributes select(){
        double w;
        double h;
        Integer texture;
        switch(Math.floor(Math.random()*14)){
            case 0:
                w=75;
                h=38;
                texture = R.drawable.house1;
                break;
            case 1:
                w=39;
                h=36;
                texture = R.drawable.house2;
                break;
            case 2:
                w=35;
                h=47;
                texture = R.drawable.house3;
                break;
            case 3:
                w=40;
                h=35;
                texture = R.drawable.house4;
                break;
            case 4:
                w=69;
                h=91;
                texture = R.drawable.house5;
                break;
            case 5:
                w=54;
                h=141;
                texture = R.drawable.tower1;
                break;
            case 6:
                w=54;
                h=143;
                texture = R.drawable.tower2;
                break;
            case 7:
                w=36;
                h=71;
                texture = R.drawable.tower3;
                break;
            case 8:
                w=72;
                h=144;
                texture = R.drawable.tower4;
                break;
            case 9:
                w=39;
                h=124;
                texture = R.drawable.tower5;
                break;
            case 10:
                w=33;
                h=130;
                texture = R.drawable.tower6;
                break;
            case 11:
                w=25;
                h=125;
                texture = R.drawable.tower7;
                break;
            case 12:
                w=40;
                h=101;
                texture = R.drawable.tower8;
                break;
            case 13:
                w=46;
                h=82;
                texture = R.drawable.tower9;
                break;
        }
        return new SpriteAttributes(w, h, texture);
    }
    
    public Building(double x, double y){
        super(x, y, 0, 0);

        SpriteAttributes attributes = Building.select();
        this.w = attributes.w;
        this.h = attributes.h;
        
        this.addTexture(attributes.texture);

        Sprite.pushSprite(this);
    }
    
}