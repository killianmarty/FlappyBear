package com.uqac.flappybear;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import org.w3c.dom.Text;

import kotlinx.coroutines.debug.AgentPremain;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private ScoreDB db;
    private Score bestScore;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        game = new Game(this, findViewById(R.id.canvas));
        game.initGame();

        db = Room.databaseBuilder(getApplicationContext(), ScoreDB.class, "game-database")
                .allowMainThreadQueries()
                .build();

        bestScore = db.scoreDao().getBestScore();
        if(bestScore != null) {
            ((TextView) findViewById(R.id.menuBestScore)).setText("Meilleur score: " + bestScore.score);
        }else{
            ((TextView) findViewById(R.id.menuBestScore)).setText("Meilleur score: 0");
        }
        //Start button listener
        findViewById(R.id.menuStartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.startGame();
            }
        });

        //Pause button listener
        findViewById(R.id.pauseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.pauseGame();
            }
        });

        findViewById(R.id.canvas).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Inputs.setTouchDown(true);
                        break;


                    case MotionEvent.ACTION_UP:
                        Inputs.setTouchDown(false);
                        break;
                }

                return true;
            }
        });

    }

    public void updateScore(Integer newScore){
        ((TextView)findViewById(R.id.score)).setText("Score : " + newScore.toString());
    }

    public void updateBestScore(Integer newScore){
        if(bestScore == null || newScore >= bestScore.score){
            bestScore = new Score(newScore);
            db.scoreDao().insertScore(bestScore);
        }
        ((TextView) findViewById(R.id.menuBestScore)).setText("Meilleur score: " + bestScore.score);
    }

    public void updateFuel(int newFuel){
        ((ProgressBar)findViewById(R.id.fuelBar)).setProgress(newFuel);
    }

    public void hideMenu() {
        findViewById(R.id.menu).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.menuBestScore)).setVisibility(View.VISIBLE);
    }

    public void showRestartMenu(){
        ((TextView)findViewById(R.id.menuTitle)).setText(R.string.gameOver_text);
        ImageView imageView = findViewById(R.id.menuStartBtn);
        MainActivity act = this;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = new Game(act, findViewById(R.id.canvas));
                game.initGame();
                game.startGame();
            }
        });
        imageView.setImageResource(R.drawable.restart_button);
        findViewById(R.id.menu).setVisibility(View.VISIBLE);
    }

    public void showPauseMenu(){
        ((TextView)findViewById(R.id.menuTitle)).setText(R.string.paused_text);
        ImageView imageView = findViewById(R.id.menuStartBtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.resumeGame();
            }
        });
        imageView.setImageResource(R.drawable.play_button);
        ((TextView) findViewById(R.id.menuBestScore)).setVisibility(View.GONE);
        findViewById(R.id.menu).setVisibility(View.VISIBLE);
    }
}
