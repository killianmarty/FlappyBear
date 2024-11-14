package com.uqac.flappybear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Renderer {

    static public HashMap<Integer, Bitmap> textureCache = new HashMap<Integer, Bitmap>();

    private GameView surfaceView;
    private Context context;
    private Canvas canvas;

    private int holderWidth;
    private int holderHeight;

    private double resolutionRatio = 0;

    public Renderer(Context context, GameView surfaceView){
        this.context = context;
        this.surfaceView = surfaceView;

        this.loadAllTextures();
    }

    private void loadAllTextures(){
        try {
            Field[] fields = R.drawable.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(int.class)) {
                    int textureId = field.getInt(null);
                    if(!Renderer.textureCache.containsKey(textureId)){
                        Bitmap texture = BitmapFactory.decodeResource(context.getResources(), textureId);
                        Renderer.textureCache.put(textureId, texture);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Position getDisplayPosition(double x, double y, double w, double h){
        double newX = (x + Settings.PLAYER_DISPLAY_X) * resolutionRatio;
        double newY = (Settings.MAX_HEIGHT - Settings.GROUND_HEIGHT - y - h) * resolutionRatio;
        double newW = w * resolutionRatio;
        double newH = h * resolutionRatio;
        return new Position(newX, newY, newW, newH);
    }

    private void drawTexture(Integer textureId, Position position, double orientation){

        Bitmap texture = Renderer.textureCache.get(textureId);
        texture = Bitmap.createScaledBitmap(texture, (int)position.w, (int)position.h, true);
        
        synchronized (surfaceView.getHolder()) {
            canvas.save();
            canvas.rotate((float)(orientation * 180 / Math.PI), (float)(position.x + position.w/2), (float)(position.y + position.h/2));
            canvas.drawBitmap(texture, (float)position.x, (float)position.y, null);
            canvas.restore();
        }
    }

    private void renderBackground(){

        synchronized (surfaceView.getHolder()) {
            canvas.drawColor(Color.rgb(204,246,255));
        }
    }

    private void renderGround(){

        synchronized (surfaceView.getHolder()) {
            Paint greenPaint = new Paint();
            greenPaint.setColor(Color.rgb(0, 150, 0));
            greenPaint.setStyle(Paint.Style.FILL);
            greenPaint.setStrokeWidth(10);
            canvas.drawRect(new Rect(0, (int)((Settings.MAX_HEIGHT -  Settings.GROUND_HEIGHT) * resolutionRatio), (int)(Settings.MAX_WIDTH * resolutionRatio), (int)(Settings.MAX_HEIGHT * resolutionRatio)), greenPaint);
        }
    }

    private void renderPlayer(Player player){
        Position playerDisplayPos = this.getDisplayPosition(0, player.y, player.w, player.h);
        this.drawTexture(player.currentTexture, playerDisplayPos, -player.orientation);
    }

    private void renderSprite(Sprite sprite, double referenceX){
        Position spriteDisplayPos = getDisplayPosition(sprite.x - referenceX, sprite.y, sprite.w, sprite.h);
        this.drawTexture(sprite.currentTexture, spriteDisplayPos, sprite.orientation);
    }

    public void render(Player player, ArrayList<Sprite> sprites, ArrayList<Sprite> backgroundSprites){

        if(!surfaceView.ready){
            return;
        }

        double referenceX = player.x;

        this.holderHeight = surfaceView.getHeight();
        this.holderWidth = surfaceView.getWidth();

        this.resolutionRatio = (double)holderHeight / Settings.MAX_HEIGHT;

        try {
            surfaceView.getHolder().setFixedSize(holderWidth, holderHeight);
            canvas = surfaceView.getHolder().lockCanvas();

            renderBackground();

            for (int i = 0; i < backgroundSprites.size(); i++) {
                renderSprite(backgroundSprites.get(i), referenceX);
            }

            renderGround();

            for (int i = 0; i < sprites.size(); i++) {
                renderSprite(sprites.get(i), referenceX);
            }

            renderPlayer(Game.game.player);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (canvas != null) {
                surfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

}
