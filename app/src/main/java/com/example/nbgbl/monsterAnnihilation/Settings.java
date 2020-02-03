package com.example.nbgbl.monsterAnnihilation;

import java.io.Serializable;

public class Settings implements Serializable {

    private int difficulty;
    private int volume;
    private int highestScore;
    private String highScoreName;
    private boolean Sound;
    private boolean isDoneStoring;
    private boolean newHighScore;
   
    public Settings(int difficulty,int volume, int highestScore,
     String highScoreName, boolean Sound,boolean isDoneStoring){

        this.difficulty = difficulty;
        this.volume = volume;
        this.highestScore = highestScore;
        this.highScoreName = highScoreName;
        this.Sound = Sound;
        this.isDoneStoring = isDoneStoring;
        setNewHighScore(false);
    }
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
   

    public int getDifficulty(){
        return difficulty;
    }




    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String getHighScoreName() {
        return highScoreName;
    }

    public void setHighScoreName(String highScoreName) {
        this.highScoreName = highScoreName;
    }

    public boolean isSound() {
        return Sound;
    }

    public void setSound(boolean Sound) {
        this.Sound = Sound;
    }

   /* public HighScoreDataBase getHighScoreDataBase() {
        return highScoreDataBase;
    }

    public void setHighScoreDataBase(HighScoreDataBase highScoreDataBase) {
        this.highScoreDataBase = highScoreDataBase;
    }
     public void getHighScore( final HighScoreDataBase highScoreDataBase) {

        // retrieves high score from database
        // only one high score persists at any given time.
        //query is made to retrieve all high scores. but insert operation
        // also deletes all entities
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isDoneStoring == false);
                // Log.i("A","done storing");
                HighScoreEntity highScoreEntity =   highScoreDataBase.daoAccess () .fetchHighScore();
                if(highScoreEntity == null){
                    //Log.i("A","highscoreentity is null");
                }
                setHighScoreName(highScoreEntity.getPlayerName());
                // Log.i("A","highest score name after getting got is"+highScoreEntity.getPlayerName());
                setHighestScore(highScoreEntity.getScore());
                //Log.i("A","highest score  after getting got is"+highScoreEntity.getScore());

            }
        }) .start();

    }
     public void storeHighScore(final HighScoreDataBase highScoreDataBase,final int score){
        // this stores a high score  and a name in the sqllite
        // database

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("A","store high score function of settings object called");
                HighScoreEntity highScoreEntity=new HighScoreEntity();
                highScoreEntity.setScore(score);
                highScoreEntity.setPlayerName(getHighScoreName());
                HighScoreEntity highScoreEntity1 = highScoreDataBase.daoAccess().fetchHighScore();
                if(highScoreEntity1 != null) highScoreDataBase.daoAccess().deleteHighScoreEntity(highScoreEntity1);
                highScoreDataBase.daoAccess () . insertScore(highScoreEntity);
                isDoneStoring = true;
            }
        }) .start();
    }*/
   @Override
    public String toString(){
       return ""+ highScoreName+" " +Integer.toString(highestScore);
   }

    public boolean isNewHighScore() {
        return newHighScore;
    }

    public void setNewHighScore(boolean newHighScore) {
        this.newHighScore = newHighScore;
    }
}
