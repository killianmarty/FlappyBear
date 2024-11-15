package com.uqac.flappybear;

public class SpriteAttributes {

    private double w;
    private double h;
    private Integer texture;

    public SpriteAttributes(double w, double h, Integer texture){
        this.w = w;
        this.h = h;
        this.texture = texture;
    }

    public double getWidth(){
        return w;
    }

    public double getHeight(){
        return h;
    }

    public Integer getTexture(){
        return texture;
    }
}
