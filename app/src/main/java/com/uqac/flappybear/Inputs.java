package com.uqac.flappybear;

public class Inputs {
    static private Boolean touchDown = false;

    static public void setTouchDown(Boolean value){
        touchDown = value;
    }

    static public Boolean getTouchDown(){
        return touchDown;
    }
}
