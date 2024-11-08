package com.uqac.flappybear;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Date;

public class Sprite {

    static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    static ArrayList<Sprite> backgroundSprites = new ArrayList<Sprite>();

    static int lastGeneration = 0;
    static double generationFrequency = Settings.MIN_GENERATION_FREQUENCY;

    double x;
    double y;
    double w;
    double h;

    float orientation;
    ArrayList<Bitmap> textures;
    int textureChangeFrequency;
    int currentTextureIndex;
    double currentTextureAge;
    Bitmap currentTexture;

    public Sprite(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.orientation = 0;

        this.textures = new ArrayList<Bitmap>();
        this.textureChangeFrequency = 0;
        this.currentTextureIndex = -1;
        this.currentTextureAge = 0;
        this.currentTexture = null;
    }

    static void updateLastGenerationDate() {
        Date date = new Date();
        updateLastGenerationDate((int) date.getTime());
    }

    static void updateLastGenerationDate(int generationDate){
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
        return (this.x + this.w >= sprite.x && this.x <= sprite.x + sprite.w && this.y + this.h >= sprite.y && this.y <= sprite.y + sprite.h);
    }

    void collide(Sprite sprite){
        Game.endGame();
        return;
    }

    void uncollide(Sprite sprite){
        return;
    }

    void addTexture(String src){

//        if(!Sprite.textureCache[src]){
//            let tmpTexture = new Image();
//            tmpTexture.src = src;
//            Sprite.textureCache[src] = tmpTexture;
//        }
//
//        this.textures.add(Sprite.textureCache[src]);
//        this.currentTextureIndex = 0;
//        this.currentTexture = this.textures[0];

    }

    double getRightBoundX(){
        return this.x + this.w;
    }

}
