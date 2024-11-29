package com.uqac.flappybear;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1)
public abstract class ScoreDB extends RoomDatabase {
    public abstract ScoreDao scoreDao();
}
