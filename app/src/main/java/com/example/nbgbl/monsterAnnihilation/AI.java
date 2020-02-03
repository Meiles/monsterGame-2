package com.example.nbgbl.monsterAnnihilation;

import android.util.Log;

import java.util.ArrayList;

public class AI {
    private int numberOfLives;
    private boolean monstersAllDead;
    private int numberOfMonstersKilled;
    private int numberOfMonsters;
    private boolean won;
    private int level;
    private boolean bombExploded;
    private boolean bulletOverlap;
    private int numberOfPowerUps;
    private boolean levelChanged;

    private Graphics graphics;
    private Settings settings;
    private PlayScore playScore;
    public AI(Graphics graphics, Settings settings,
              PlayScore playScore)
    {

        this.graphics = graphics;
        this.settings= settings;
        this.playScore = playScore;
        level = 1;
        setWon(false);
        setNumberOfLives(3);
        setMonstersAllDead(false);
        setNumberOfMonstersKilled(0);
        setNumberOfMonsters(0);
        setNumberOfPowerUps(0);
        levelChanged = false;
    }

    public boolean winningCondition(){
    return isWon();
    }
    public void update(){
    if(getNumberOfMonsters() < getNumberOfMonstersKilled()) {
        setMonstersAllDead(true);

    }
    if(isMonstersAllDead()){
        increaseLevel();
        setMonstersAllDead(false);
    }
   if(level == 4) setWon(true);
    //Log.i("I", " ai update funciton finished");

    }
    public void generateActors(){
    for(int i = 0; i < 10 * level; i++){
        graphics.createMonster(i);
    }
    }

    public void killAllMonsters(){
    graphics.killAllMonsters();
    }
    public void showEndGameMessage(){

    }
    public boolean isLevelChanged(){
        return levelChanged;
    }
    public void increaseLevel(){
    if(level == 3) setWon(true);
    generateActors();
    setLevel(level+1);
    graphics.increaseMonstersLevels();
    graphics.increasePowUpLevel();
    levelChanged = true;

    }
    public int getLevel(){
        return  level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public int getNumberOfMonstersKilled() {
        return numberOfMonstersKilled;
    }


    public int getGameDifficulty(){
        return settings.getDifficulty();
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public boolean isMonstersAllDead() {
        return monstersAllDead;
    }

    public void setMonstersAllDead(boolean monstersAllDead) {
        this.monstersAllDead = monstersAllDead;
    }

    public void setNumberOfMonstersKilled(int numberOfMonstersKilled) {
        this.numberOfMonstersKilled = numberOfMonstersKilled;
    }

    public int getNumberOfMonsters() {
        return numberOfMonsters;
    }

    public void setNumberOfMonsters(int numberOfMonsters) {
        this.numberOfMonsters = numberOfMonsters;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }


    public int getNumberOfPowerUps() {
        return numberOfPowerUps;
    }

    public void setNumberOfPowerUps(int numberOfPowerUps) {
        this.numberOfPowerUps = numberOfPowerUps;
    }
}
