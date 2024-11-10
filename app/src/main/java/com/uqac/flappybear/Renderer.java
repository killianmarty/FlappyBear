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

public class Renderer {
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
        double newY = (Settings.MAX_HEIGHT - y - h)* this.verticalRatio;
        double newW = w * this.verticalRatio;
        double newH = h * this.verticalRatio;
        return new Position(newX, newY, newW, newH);
    }

    private void drawTexture(Bitmap texture, Position position, double orientation){
        synchronized (surfaceView.getHolder()) {
            //surfaceView.onDraw(canvas);
            canvas.drawBitmap(texture, 10, 10, null);
        }
    }

    private void renderBackground(){

        synchronized (surfaceView.getHolder()) {
            canvas.drawColor(Color.BLUE);
        }
    }

    private void renderGround(){

        synchronized (surfaceView.getHolder()) {
            Paint greenPaint = new Paint();
            greenPaint.setColor(Color.rgb(0, 255, 0));
            greenPaint.setStyle(Paint.Style.FILL);
            greenPaint.setStrokeWidth(10);
            canvas.drawRect(new Rect(surfaceView.getLeft(), surfaceView.getHeight() -  Settings.GROUND_HEIGHT * surfaceView.getHeight() / Settings.MAX_HEIGHT, surfaceView.getRight(), surfaceView.getBottom()), greenPaint);
        }
    }

    private void renderSprite(Sprite sprite, double referenceX){
        Position spriteDisplayPos = getDisplayPosition(sprite.x - referenceX, sprite.y, sprite.w, sprite.h);
        Bitmap texture = BitmapFactory.decodeResource(context.getResources(), sprite.currentTexture);
        texture = Bitmap.createScaledBitmap(texture, (int)spriteDisplayPos.w, (int)spriteDisplayPos.h, true);
        this.drawTexture(texture, spriteDisplayPos, sprite.orientation);
    }

    public void render(Player player, ArrayList<Sprite> sprites, ArrayList<Sprite> backgroundSprites){
        this.horizontalRatio = (double) surfaceView.getWidth() / Settings.MAX_WIDTH;
        this.verticalRatio = (double) surfaceView.getHeight() / Settings.MAX_HEIGHT;

        double referenceX = 50;

        if(!surfaceView.ready){
            return;
        }

        canvas = null;
        try {
            canvas = surfaceView.getHolder().lockCanvas();

            renderBackground();
            renderGround();
            for (int i = 0; i < sprites.size(); i++) {
                renderSprite(sprites.get(i), referenceX);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (canvas != null) {
                surfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

}
