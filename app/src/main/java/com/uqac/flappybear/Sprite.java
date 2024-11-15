package com.uqac.flappybear;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Interpolator;

import java.util.ArrayList;
import java.util.Date;

public class Sprite {

    static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    static ArrayList<Sprite> backgroundSprites = new ArrayList<Sprite>();

    static int lastGeneration = 0;
    static double generationFrequency = Settings.MIN_GENERATION_FREQUENCY;

    protected double x;
    protected double y;
    protected double w;
    protected double h;
    protected double orientation;

    protected int textureChangeFrequency;

    private ArrayList<Integer> textures;
    private int currentTextureIndex;
    private double currentTextureAge;
    private Integer currentTexture;

    public Sprite(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.orientation = 0;

        this.textures = new ArrayList<Integer>();
        this.textureChangeFrequency = 0;
        this.currentTextureIndex = -1;
        this.currentTextureAge = 0;
        this.currentTexture = null;
    }

    private static void updateLastGenerationDate() {
        Date date = new Date();
        updateLastGenerationDate((int) date.getTime());
    }

    private static void updateLastGenerationDate(int generationDate){
        Sprite.lastGeneration = generationDate;
        if(Sprite.generationFrequency < Settings.MAX_GENERATION_FREQUENCY){
            Sprite.generationFrequency += Settings.GENERATION_FREQUENCY_GAP;
        }
    }

    static int getLastGenerationAge(){
        return (int)(new Date()).getTime() - Sprite.lastGeneration;
    }

    static void pushSprite(Sprite sprite){
        Sprite.sprites.add(sprite);
        Sprite.updateLastGenerationDate();
    }

    static void pushBackgroundSprite(Sprite backgroundSprite){
        Sprite.backgroundSprites.add(backgroundSprite);
    }

    static Sprite getLastSprite(){
        return Sprite.sprites.get(Sprite.sprites.size() - 1);
    }

    static void reset(){
        Sprite.sprites = new ArrayList<Sprite>();
        Sprite.backgroundSprites = new ArrayList<Sprite>();
        Sprite.lastGeneration = 0;
        Sprite.generationFrequency = Settings.MIN_GENERATION_FREQUENCY;
    }

    void updateTextures(double dt){
        this.currentTextureAge += dt;
        if(this.textures.size() > 1 && this.textureChangeFrequency > 0){
            if(this.currentTextureAge > 1.0/this.textureChangeFrequency){
                this.currentTextureIndex = (this.currentTextureIndex + 1) % this.textures.size();
                this.currentTextureAge = 0;
                this.currentTexture = this.textures.get(this.currentTextureIndex);
            }
        }
    }

    void update(double dt){
        this.updateTextures(dt);
    }

    Boolean checkCollide(Sprite sprite){
        return (this.x + this.w >= sprite.getX() && this.getX() <= sprite.getX() + sprite.getWidth() && this.y + this.h >= sprite.getY() && this.y <= sprite.getY() + sprite.getHeight());
    }

    void collide(Sprite sprite){
        Game.game.endGame();
    }

    void uncollide(Sprite sprite){
    }

    void addTexture(Integer drawableId){

        textures.add(drawableId);
        currentTextureIndex = 0;
        currentTexture = textures.get(0);

    }

    double getRightBoundX(){
        return this.x + this.w;
    }



    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getWidth(){
        return w;
    }

    public double getHeight(){
        return h;
    }

    public double getOrientation(){
        return orientation;
    }

    public Integer getCurrentTexture(){
        return currentTexture;
    }
}
