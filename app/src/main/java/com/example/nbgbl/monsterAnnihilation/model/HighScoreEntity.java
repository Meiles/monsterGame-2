package com.example.nbgbl.monsterAnnihilation.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class HighScoreEntity {
    @NonNull
    @PrimaryKey
    private int score;
    private String playerName;

    public HighScoreEntity() {
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score= score; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName= playerName; }

}
