package com.uqac.flappybear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Renderer {

    static public HashMap<Integer, Bitmap> textureCache = new HashMap<Integer, Bitmap>();

    private GameView surfaceView;
    private Context context;
    private Canvas canvas;

    private double horizontalRatio;
    private double verticalRatio;

    public Renderer(Context context, GameView surfaceView){
        this.context = context;
        this.surfaceView = surfaceView;

        this.horizontalRatio = (double) surfaceView.getWidth() / Settings.MAX_WIDTH;
        this.verticalRatio = (double) surfaceView.getHeight() / Settings.MAX_HEIGHT;
    }

    private Position getDisplayPosition(double x, double y, double w, double h){
        double newX = (x + Settings.PLAYER_DISPLAY_X) * this.verticalRatio;
        double newY = (Settings.MAX_HEIGHT - Settings.GROUND_HEIGHT - y - h) * this.verticalRatio;
        double newW = w * this.verticalRatio;
        double newH = h * this.verticalRatio;
        return new Position(newX, newY, newW, newH);
    }

    private void drawTexture(Integer textureId, Position position, double orientation){
        
        if(!Renderer.textureCache.containsKey(textureId)){
            Bitmap texture = BitmapFactory.decodeResource(context.getResources(), textureId);
            Sprite.textureCache.put(textureId, texture);
        }

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
            canvas.drawRect(new Rect(surfaceView.getLeft(), (int)(surfaceView.getHeight() -  Settings.GROUND_HEIGHT * verticalRatio), surfaceView.getRight(), surfaceView.getBottom()), greenPaint);
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
        this.horizontalRatio = (double) surfaceView.getWidth() / Settings.MAX_WIDTH;
        this.verticalRatio = (double) surfaceView.getHeight() / Settings.MAX_HEIGHT;

        double referenceX = player.x;

        if(!surfaceView.ready){
            return;
        }

        canvas = null;
        try {
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
