package com.uqac.flappybear;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

public class Game extends Thread {

    static Game game;

    //Globals
    public Player player;
    public Renderer renderer;

    //Frame managment
    public long lastFrameDate;

    //Playing & Pausing managment
    public Boolean playing = false;
    public Boolean paused = false;

    //Audio managment
    public Boolean muted = true;
    //static gameOverSound;

    ///////////////////////////

    //public final long FPS = 10;
    public GameView view;
    public Activity activity;


    public Game(Activity activity, GameView view) {

        this.view = view;
        this.activity = activity;
        renderer = new Renderer(view.getContext(), view);

        player = new Player(0, 50, 30, 20, 60, 0);

        lastFrameDate = System.currentTimeMillis();

        game = this;

    }
    
    public void initGame(){
        resetGame();
    
        // gameOverSound = document.createElement("audio");
        // gameOverSound.src = "assets/audio/gameover.wav";
        
        if(!muted) player.startAudio();
        
        startMainLoop();
    }
    
    public void resetGame(){
        Sprite.reset();
        player = new Player(0, 50, 30, 20, 60, 0);
        lastFrameDate = System.currentTimeMillis();
    
        resetDisplay();
    }
    
    public void resetDisplay(){
        renderer = new Renderer(view.getContext(), view);
    }
    
    public double computeDeltaTime(){
         long currentDate = System.currentTimeMillis();
         long dt = currentDate - lastFrameDate;
         lastFrameDate = currentDate;
         return (double) dt /1000;
    }
    
    public void generateCloud(){
        if(Cloud.lastRightBoundX < player.x + Settings.MAX_WIDTH*0.9 && Math.random() > 0.2){
    
            double x = player.x + Settings.MAX_DISPLAY_WIDTH;
            double y = Math.random()*Settings.MAX_HEIGHT/0.5 + 1.5*Settings.GROUND_HEIGHT;
    
            new Cloud(x, y);
    
        }
    }
    
    public void generateBirds(){
        if(Birds.lastRightBoundX < player.x + Settings.MAX_WIDTH*0.2 && Math.random() > 0.05){
    
            double x = player.x + Settings.MAX_DISPLAY_WIDTH;
            double y = Math.random()*Settings.MAX_HEIGHT/0.5 + 1.5*Settings.GROUND_HEIGHT;
    
            new Birds(x, y);
    
        }
    }
    
    public void generateMountain(){
        double x = player.x + Settings.MAX_DISPLAY_WIDTH;
        double y = -5;
        if(Mountain.lastRightBoundX < x && Math.random()<0.001){
            
            new Mountain(x, y);
    
        }
    }
    
    public void generateAirport(){
        if(player.fuel < 25 && Airport.lastRightBoundX < player.x - 500){
    
            double newAirportX = (Sprite.getLastSprite().getRightBoundX() + 10);
            if(newAirportX < Settings.MAX_DISPLAY_WIDTH){
                newAirportX = player.x + Settings.MAX_DISPLAY_WIDTH;
            }
    
            new Airport(newAirportX, -7);
    
        }
    }
    
    public void generateSprite(){
        double spawnX = player.x + Settings.MAX_DISPLAY_WIDTH;
    
        if(Sprite.getLastGenerationAge() > 1000/Sprite.generationFrequency){
            if(Sprite.sprites.isEmpty() || Sprite.getLastSprite().getRightBoundX() < spawnX){
                
                int rand = (int)(Math.random()*21);
    
                if(rand <= 14) new Building(spawnX, (-Math.random()*4));
                else if(rand <= 17) new Vegetation(spawnX, -7);
                else if(rand <= 18) new Baloon(spawnX, (Settings.MIN_VEHICLE_GENERATION_HEIGHT + Math.random() * Settings.VEHICLE_GENERATION_HEIGHT_GAP));
                else if(rand <= 19) new Helicopter(spawnX, (Settings.MIN_VEHICLE_GENERATION_HEIGHT + Math.random() * Settings.VEHICLE_GENERATION_HEIGHT_GAP));
                else if(rand <= 20) new FighterJet(spawnX, (Settings.MIN_VEHICLE_GENERATION_HEIGHT + Math.random() * Settings.VEHICLE_GENERATION_HEIGHT_GAP));
    
            }
        }
    }

    public void setRunning(boolean run) {
        playing = run;
    }

    @Override
    public void run() {

        while(true){
            if(!isInterrupted()){
                frame();
            }
        }

    }

    public void frame(){
        double dt = computeDeltaTime();

        player.throttle = Inputs.touchDown;
        player.update(dt);

        //Generations
        generateMountain();
        generateCloud();
        generateBirds();

        if(playing && !paused) {
            generateSprite();
            generateAirport();
        }

        System.out.println("Nb of background sprites: " + Sprite.backgroundSprites.size());
        System.out.println("Nb of sprites: " + Sprite.sprites.size());

        //Background Sprites loop
        for (int i = 0; i < Sprite.backgroundSprites.size(); i++) {

            //Delete background sprite if not visible anymore
            if(Sprite.backgroundSprites.get(i).x < player.x - 5000){
                Sprite.backgroundSprites.remove(i);
                continue;
            }

            Sprite.backgroundSprites.get(i).update(dt);

        }

        //Main Sprites loop
        for (int i = 0; i < Sprite.sprites.size(); i++) {

            //Delete sprite if not visible anymore
            if(Sprite.sprites.get(i).x < player.x - 5000){
                Sprite.sprites.remove(i);
                continue;
            }

            Sprite.sprites.get(i).update(dt);

            if(player.checkCollide(Sprite.sprites.get(i))){
                Sprite.sprites.get(i).collide(player);
            }
            else{
                Sprite.sprites.get(i).uncollide(player);
            }

        }

        //Check for ground crash
        if(player.checkGroundCollision()) endGame();

        renderer.render(player, Sprite.sprites, Sprite.backgroundSprites);

        //Update DOM Score and Fuel level
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)activity).updateScore((int)(player.score));
                ((MainActivity)activity).updateFuel((int)player.fuel);
            }
        });

        long execTime = System.currentTimeMillis() - lastFrameDate;
        if(execTime < 16) {
            try {
                sleep(16 - execTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startMainLoop(){
        lastFrameDate = System.currentTimeMillis();
        start();
    }

    public void stopMainLoop(){
        interrupt();
    }

    public void startGame(){
        //Set playing and paused values
        System.out.println("lace");
        playing = true;
        paused = false;

        if(!muted) player.startAudio();

        //Note: we don't need to start the main loop because it's already running because of the prelaunch animation

        //Hide the menu
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)activity).hideMenu();
            }
        });
    }

    public void endGame(){
        playing = false;
        paused = false;

        //if(!muted) gameOverSound.play();
        player.stopAudio();

        stopMainLoop();

        //Show the restart menu
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)activity).showRestartMenu();
            }
        });
    }

    public void pauseGame(){
        paused = true;

        player.stopAudio();

        stopMainLoop();

        //Show the pause menu
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)activity).showPauseMenu();
            }
        });
    }

    public void resumeGame(){
        paused = false;

        if(!muted) player.startAudio();

        startMainLoop();

        //Hide the menu
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)activity).hideMenu();
            }
        });
    }
}
