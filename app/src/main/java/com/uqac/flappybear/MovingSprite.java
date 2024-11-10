package com.uqac.flappybear;

public class MovingSprite extends Sprite{
    double vx;
    double vy;
    public MovingSprite(double x, double y, double w, double h, double vx, double vy){
            super(x, y, w, h);
            this.vx = vx;
            this.vy = vy;
        }

        @Override
        void update(double dt){
            this.updateTextures(dt);

            this.x += this.vx*dt;
            this.y += this.vy*dt;
        }
}
