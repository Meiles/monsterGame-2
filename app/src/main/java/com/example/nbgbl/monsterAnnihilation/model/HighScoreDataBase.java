package com.example.nbgbl.monsterAnnihilation.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {HighScoreEntity.class},version =1,exportSchema = false)
public abstract class HighScoreDataBase extends RoomDatabase {
public abstract DaoAccess daoAccess();
}
