package com.uqac.flappybear;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int score;

    // Constructeur
    public Score(int score) {
        this.score = score;
    }
}
