package com.example.nbgbl.monsterAnnihilation.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.nbgbl.monsterAnnihilation.model.HighScoreEntity;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertScore(HighScoreEntity highScoreEntities );
    //@Insert
   // void insertMultipleMovies (List<HighScoreEntity> highScoreEntityList);
    @Query("SELECT * FROM highScoreEntity ")
    HighScoreEntity fetchHighScore();
    @Update
    void updateHighScoreEntity (HighScoreEntity highScoreEntities );
    @Delete
    void deleteHighScoreEntity (HighScoreEntity highScoreEntities );

}
