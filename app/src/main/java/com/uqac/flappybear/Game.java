package com.uqac.flappybear;

import android.graphics.Canvas;

public class Game extends Thread {
    static void endGame(){
        return;
    }

    static final long FPS = 10;
    private GameView view;
    private boolean running = false;

    Player player;

    private Renderer renderer;

    public Game(GameView view) {

        this.view = view;
        this.renderer = new Renderer(view.getContext(), view);

        this.player = new Player(0, 50, 30, 20, 60, 0);

    }

    public void setRunning(boolean run) {
        running = run;

        new Baloon(300, 300);
        System.out.println(Sprite.sprites);
    }

    @Override
    public void run() {

        long ticksPS = 1000 / FPS;

        long startTime;

        long sleepTime;

        while (running) {

            startTime = System.currentTimeMillis();

            renderer.render(player, Sprite.sprites, Sprite.backgroundSprites);

            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);

            try {

                if (sleepTime > 0)

                    sleep(sleepTime);

                else

                    sleep(10);

            } catch (Exception e) {}

        }

    }
}
