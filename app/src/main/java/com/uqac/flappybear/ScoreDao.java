package com.uqac.flappybear;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ScoreDao {

    // Insérer un score dans la table
    @Insert
    void insertScore(Score score);

    // Récupérer le meilleur score
    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT 1")
    Score getBestScore();
}