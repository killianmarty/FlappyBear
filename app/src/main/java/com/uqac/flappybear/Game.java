package com.uqac.flappybear;

import android.graphics.Canvas;

public class Game extends Thread {

    //Globals
    static Player player;
    static Renderer renderer;

    //Frame managment
    //static interval;
    static int lastFrameDate;

    //Playing & Pausing managment
    static Boolean playing = false;
    static Boolean paused = false;

    //Audio managment
    static Boolean muted = true;
    //static gameOverSound;

    ///////////////////////////

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

    static void initInputs(){
        // let canvas = document.getElementById("canvas");
    
        // canvas.addEventListener("mousedown", ()=>{Game.player.throttle = true});
        // canvas.addEventListener('mouseup', ()=>{Game.player.throttle = false});
    
        // canvas.addEventListener("touchstart", ()=>{Game.player.throttle = true});
        // canvas.addEventListener("touchend", ()=>{Game.player.throttle = false});
    
        // window.addEventListener("resize", ()=>{Game.resetDisplay()});
    }
    
    static void initGame(){
        Game.initInputs();
        Game.resetGame();
    
        // Game.gameOverSound = document.createElement("audio");
        // Game.gameOverSound.src = "assets/audio/gameover.wav";
        
        if(!Game.muted) Game.player.startAudio();
        
        Game.startMainLoop();
    }
    
    static void resetGame(){
        Sprite.reset();
        Game.player = new Player(0, 50, 30, 20, 60, 0);
        //Game.lastFrameDaye = Date.now();
    
        Game.resetDisplay();
    }
    
    static void resetDisplay(){
        // let canvas = document.getElementById("canvas");
        // canvas.width = canvas.offsetWidth;
        // canvas.height = canvas.offsetHeight;
        // Game.rendered = new Renderer(canvas);
    }
    
    static computeDeltaTime(){
        // let currentDate = Date.now();
        // let dt = currentDate - Game.lastFrameDaye;
        // Game.lastFrameDaye = currentDate;
        // return dt/1000;
    }
    
    static generateCloud(){
        if(Cloud.lastRightBoundX < Game.player.x + Settings.MAX_WIDTH*0.9 && Math.random() > 0.2){
    
            let x = Game.player.x + Settings.MAX_DISPLAY_WIDTH;
            let y = Math.random()*Settings.MAX_HEIGHT/0.5 + 1.5*Settings.GROUND_HEIGHT;
    
            new Cloud(x, y);
    
        }
    }
    
    static generateBirds(){
        if(Birds.lastRightBoundX < Game.player.x + Settings.MAX_WIDTH*0.2 && Math.random() > 0.05){
    
            let x = Game.player.x + Settings.MAX_DISPLAY_WIDTH;
            let y = Math.random()*Settings.MAX_HEIGHT/0.5 + 1.5*Settings.GROUND_HEIGHT;
    
            new Birds(x, y);
    
        }
    }
    
    static generateMountain(){
        let x = Game.player.x + Settings.MAX_DISPLAY_WIDTH;
        let y = -5;
        if(Mountain.lastRightBoundX < x && Math.random()<0.001){
            
            new Mountain(x, y);
    
        }
    }
    
    static generateAirport(){
        if(Game.player.fuel < 25 && Airport.lastRightBoundX < Game.player.x - 500){
    
            double newAirportX = (Sprite.getLastSprite().getRightBoundX() + 10);
            if(newAirportX < Settings.MAX_DISPLAY_WIDTH){
                newAirportX = Game.player.x + Settings.MAX_DISPLAY_WIDTH;
            }
    
            new Airport(newAirportX, -7)
    
        }
    }
    
    static generateSprite(){
        double spawnX = Game.player.x + Settings.MAX_DISPLAY_WIDTH;
    
        if(Sprite.getLastGenerationAge() > 1000/Sprite.generationFrequency){
            if(Sprite.sprites.length == 0 || Sprite.getLastSprite().getRightBoundX() < spawnX){
                
                int rand = Math.floor(Math.random()*21);
    
                if(rand <= 14) new Building(spawnX, (-Math.random()*4));
                else if(rand <= 17) new Vegetation(spawnX, -7);
                else if(rand <= 18) new Baloon(spawnX, (Settings.MIN_VEHICLE_GENERATION_HEIGHT + Math.random() * Settings.VEHICLE_GENERATION_HEIGHT_GAP));
                else if(rand <= 19) new Helicopter(spawnX, (Settings.MIN_VEHICLE_GENERATION_HEIGHT + Math.random() * Settings.VEHICLE_GENERATION_HEIGHT_GAP));
                else if(rand <= 20) new FighterJet(spawnX, (Settings.MIN_VEHICLE_GENERATION_HEIGHT + Math.random() * Settings.VEHICLE_GENERATION_HEIGHT_GAP));
    
            }
        }
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

    static void endGame(){
        return;
    }
}
