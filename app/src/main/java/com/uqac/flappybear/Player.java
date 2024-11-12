package com.uqac.flappybear;

public class Player extends MovingSprite{
    public double fuel;
    public Boolean throttle;
    public Boolean landed;
    public int score;

    public Player(double x, double y, double w, double h, double vx, double vy){
        super(x, y, w, h, vx, vy);
        this.fuel = 100;
        this.throttle = false;
        this.landed = false;
        this.score = 0;

        this.addTexture(R.drawable.plane);

        //this.initAudio();
    }

    public void initAudio() {
        // this.audio = document.createElement("audio");
        // this.audio.src = "assets/audio/plane.wav";
        // this.audio.loop = true;
    }

    public void startAudio(){
        // this.audio.play();
    }

    public void stopAudio(){
        // this.audio.pause();
    }

    public Boolean checkGroundCollision(){
        return (this.y <= 0 && !this.landed);
    }

    //Overide to reduce the hitbox
    @Override
    public Boolean checkCollide(Sprite sprite){
        double horizontalReduction = 0.75;
        double verticalReduction = 0.50;
        double x = this.x + this.w * horizontalReduction/2;
        double y = this.y + this.h * verticalReduction/2;
        double w = this.w * (1-horizontalReduction);
        double h = this.h * (1-verticalReduction);
        return (x + w >= sprite.x && x <= sprite.x + sprite.w && y + h >= sprite.y && y <= sprite.y + sprite.h);
    }

    @Override
    public void update(double dt){
        this.updateTextures(dt);

//        if (this.audio) {
//            double pitch = this.throttle ? 0.5 : 2;
//            float volume = this.orientation / (Settings.MAX_UP_ROTATION * 3) + 0.8;
//            if(volume > 1) volume = 1;
//            if(volume < 0) volume = 0;
//            // this.audio.playbackRate = pitch;
//            // this.audio.volume = volume;
//        }

        if(Game.game.playing && !Game.game.paused){

            if(this.throttle && this.orientation < Settings.MAX_UP_ROTATION && this.fuel > 0){
                this.fuel -= Settings.FUEL_PERCENT_PER_SECOND * dt;
                this.vy += Settings.GRAVITY * dt;
                this.landed = false;
            }else{
                this.vy -= Settings.GRAVITY * dt;
            }

        }
        
        this.x += this.vx*dt;
        this.y += this.vy*dt;

        this.orientation = Math.atan(this.vy/this.vx);
        if(Game.game.playing && !Game.game.paused) this.score += (int)(this.vx * dt * Settings.SCORE_PER_PIXEL);

        
        //Landing managment
        if(this.landed){
            this.fuel += Settings.REFUEL_PERCENT_PER_SECOND * dt;
            this.y=0;
            this.vy = 0;
            this.orientation = 0;
        }

        //Max height managment
        if(this.y + this.h > Settings.MAX_HEIGHT){
            this.y = Settings.MAX_HEIGHT - this.h;
            this.vy = 0;
            this.orientation = 0;
        }

    }
}
