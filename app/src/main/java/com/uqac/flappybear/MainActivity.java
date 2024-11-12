package com.uqac.flappybear;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    private Game game;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        game = new Game(this, (GameView)findViewById(R.id.canvas));
        game.initGame();

        ((ImageView)findViewById(R.id.menuStartBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.startGame();
            }
        });

    }

    public void updateScore(Integer newScore){
        ((TextView)findViewById(R.id.score)).setText("Score : " + newScore.toString());
    }

    public void hideMenu() {
        ((LinearLayout)findViewById(R.id.menu)).setVisibility(View.GONE);
    }

    public void showRestartMenu(){
        ((TextView)findViewById(R.id.menuTitle)).setText(R.string.gameOver_text);
        ImageView imageView = ((ImageView)findViewById(R.id.menuStartBtn));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.restartGame();
            }
        });
        imageView.setImageResource(R.drawable.restart_button);
        ((LinearLayout)findViewById(R.id.menu)).setVisibility(View.VISIBLE);
    }

    public void showPauseMenu(){
        ((TextView)findViewById(R.id.menuTitle)).setText(R.string.paused_text);
        ImageView imageView = ((ImageView)findViewById(R.id.menuStartBtn));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.resumeGame();
            }
        });
        imageView.setImageResource(R.drawable.play_button);
        ((LinearLayout)findViewById(R.id.menu)).setVisibility(View.VISIBLE);
    }
}
