package com.uqac.flappybear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;


    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override

            public void surfaceDestroyed(SurfaceHolder holder) {

            }
            @Override

            public void surfaceCreated(SurfaceHolder holder) {

                Canvas c = holder.lockCanvas(null);

                onDraw(c);

                holder.unlockCanvasAndPost(c);

            }

            @Override

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

        });

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button);
    }

    @Override

    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLUE);

        canvas.drawBitmap(bmp, 10, 10, null);

    }

}
