package com.example.nbgbl.monsterAnnihilation;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nbgbl.monsterAnnihilation.model.HighScoreDataBase;
import com.example.nbgbl.monsterAnnihilation.model.HighScoreEntity;

import java.io.Serializable;


public class PlayScore extends AppCompatActivity implements Runnable{

    private double time;
    private int score;
    private AI ai;
    private Graphics graphics;
    private Settings settings;
    private Thread playThread;
    private  boolean playing;
    private boolean movingLeft;
    private boolean movingRight;
    private  int currentPowerUp;
    private Vector3d gunDirection;
    private  boolean started;
    private static final String DATABASE_NAME = "highscore_db";
    public PlayScore(){
        time = 0;
        score = 0;
        playing = true;
        movingLeft = false;
        movingRight= false;
        setCurrentPowerUp(0);
        gunDirection = new Vector3d(0,0,0);
        started = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        graphics = new Graphics(this.getApplicationContext(), this);
       initView();


        ai = new AI(graphics,settings,this);
        graphics.setAi(ai);
        settings = (Settings) getIntent().getSerializableExtra("Settings_Extra");
        Log.i("A","Settings difficulty is "+settings.getDifficulty());
        returnSettings();
        Log.i("A","result now set in playscore constructor");

        playThread = new Thread(this);
        playThread.start();
    }
    @Override
    public void onBackPressed(){
        playing = false;
        try {
            //stopping the thread
            playThread.join();
        } catch (InterruptedException e) {
        }
        if(isHighScore()) {
            settings.setHighestScore(score);
            settings.setNewHighScore(true);
        }
        returnSettings();
        finish();
    }
    public void returnSettings(){
        Intent intent = new Intent();
        intent.putExtra("Settings_Extra",settings);
        setResult(1,intent);
        Log.i("A","return settings finished");
        Log.i("A","settings at end of returnSettings function is "+ settings);
    }
    @Override
    public void run(){
        //Log.i("A","entering run function");
while(playing){
    ai.update();
    graphics.update();
    graphics.draw();
    updateScore();
    if(ai.winningCondition()) gameOver();
}

    }
    public void initView(){
        FrameLayout game = new FrameLayout(this);

        LinearLayout gameWidgets = new LinearLayout(this);
        RelativeLayout attackButtons = new RelativeLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        // or set height to any fixed value you want
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        game.setLayoutParams(lp);
        gameWidgets.setLayoutParams(lp);
        gameWidgets.setOrientation(LinearLayout.HORIZONTAL);


        Button leftButton = new Button(this);
        leftButton.setLayoutParams(lp);
        //leftButton.getLayoutParams().width = width;
        leftButton.setBackgroundColor(Color.TRANSPARENT);
        leftButton.setGravity(Gravity.LEFT);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.bulletbutton, null);



        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        final MonsPlayer player = graphics.getPlayer();
                        movingLeft = true;
                        Thread moveThread = new Thread(new Runnable(){
                            public void run(){
                                while(movingLeft){
                                    Vector3d adjustedPosition = new Vector3d(player.getPosition());
                                    adjustedPosition = adjustedPosition.plus(new Vector3d(graphics.getPlayerBitmap().getWidth()/2.0,graphics.getPlayerBitmap().getHeight()/2.0,0));
                                    Vector3d direction = new Vector3d(event.getX(),event.getY(),0);
                                    direction = (adjustedPosition.minus(direction)).normalize();
                                    Vector3d temp = new Vector3d(0,0,0);
                                    temp = temp.plus(direction);
                                    gunDirection = temp;
                                    direction.selfScale(-0.01);

                                    player.setPosition(player.getPosition().plus(direction));

                                   // graphics.getGuns().get(0).setPosition(player.getPosition().plus(new Vector3d(30,0,0)));
                                }

                            }
                        });
                        moveThread.start();

                        return true;
                        
                    case MotionEvent.ACTION_UP:
                        movingLeft = false;
                        return true;
                }
                return false;
            }
        });


       gameWidgets.addView(leftButton);

        //gameWidgets.addView(rightButton);

        game.addView(graphics);
        game.addView(gameWidgets);
        game.addView(v);
        //game.addView(attackButtons);
        setContentView(game);

    }
    public  void shoot(View v) {

        if(!started) {
            View startView = findViewById(R.id.startText);
            startView.setVisibility(View.INVISIBLE);
            ai.increaseLevel();
        }
        started = true;
        graphics.shootBullet();
    }
    public  void launchBomb(View v){
        graphics.launchBomb();
    }
    public void updateScore(){
    // score reflects current score
        // might be done in update function graphics using booleans
        // might use increments from graphics to calculate new score based on a formula.
        // This might take time into account
        score = 0;
      //  Log.i("I","score should be 0,but it is "+score);
        score = score + (ai.getLevel()-1)*100;
       // Log.i("I","ai multiplier is "+  (ai.getLevel()-1)*100);
       // Log.i("I","score after adding level multiplier is "+score);
        score = score + ai.getNumberOfMonstersKilled()*2;
        //Log.i("I","score after adding monster number multiplier  is "+score);
        score = score + ai.getNumberOfPowerUps()*5;
    }

    public boolean hasLevelChanged(){
    //uses ai to  tell if level has changed. might take form of just update. may pass new level
        //into every graphics update function

        return ai.isLevelChanged();
    }
    public void showLevelChanged(){
    // shows level changed animation
        if(ai.isLevelChanged()){

        }
    }
    public void gameOver(){
    // shows game over animation

        if(isHighScore()) {
            settings.setHighestScore(score);
            settings.setNewHighScore(true);
        }
        playing = false;
        returnSettings();
        finish();
    }


    public boolean isHighScore(){
    // might be used to determine if first score is higher than one currently in settings.
        // will be called at end of game. If new score is higher, then settings
        // high score will change
        return score > settings.getHighestScore();
    }



    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getCurrentPowerUp() {
        return currentPowerUp;
    }

    public void setCurrentPowerUp(int currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public Vector3d getGunDirection() {
        return gunDirection;
    }

    public void setGunDirection(Vector3d gunDirection) {
        this.gunDirection = gunDirection;
    }
}
