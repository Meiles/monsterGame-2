package com.example.nbgbl.monsterAnnihilation;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.nbgbl.monsterAnnihilation.model.HighScoreDataBase;
import com.example.nbgbl.monsterAnnihilation.model.HighScoreEntity;

public class SetupMenuActivity extends AppCompatActivity {
    private String title;
    private Button optionsButton;
    private Button scoreBoardButton;
    private Button quitButton;
    private Button playButton;
    private Button volumeButton;
    private Button difficultyButton;
    private String playerName;
    private int endScore;
    private int difficulty;
    private int volume;
    private int highestScore;
    private String highScoreName;
    private boolean sound;
    private TextView highScoresTitle;
    private TextView highScoreNameView;
    private TextView highestScoreView;
    private static final String DATABASE_NAME = "highscore_db";
    private HighScoreDataBase highScoreDataBase;
    private boolean isDoneStoring;
    static final int REQUEST_CODE = 1;
    Settings settings;

    public  SetupMenuActivity(){
        sound = false;
        volume = 100;
        endScore = 0;
        highestScore = 0;
        playerName = null;
        difficulty = 1;
        isDoneStoring = false;
      
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup_menu);
        createHighScoreButton();
        highestScoreView = (TextView) findViewById(R.id.highsestScoreView);
        highScoreNameView = (TextView) findViewById(R.id.highestScoreName);
        highScoresTitle =(TextView) findViewById(R.id.high_scores_list);
       highScoreDataBase= Room.databaseBuilder(getApplicationContext(),
                HighScoreDataBase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        settings = new Settings( difficulty,volume, highestScore,
        highScoreName,  sound,isDoneStoring);
        settings.setHighScoreName("nolan");
        settings.setHighestScore(100);
       /* if(settings.getHighScoreDataBase()!= null){
            Log.i("A","database is not null");
            storeHighScore();
            Log.i("A","done storing high score");

        }*/


        getHighScore();
    }

    public void setVolume(int volume){

        settings.setVolume(volume);
    }

    
    public void setEndScore(int endScore){
        this.endScore = endScore;
    }
    public void storeHighScore(){
    // this stores a high score  and a name in the sqllite
        // database

        new Thread(new Runnable() {
            @Override
            public void run() {
                HighScoreEntity highScoreEntity=new HighScoreEntity();
                highScoreEntity.setScore( settings.getHighestScore());
                highScoreEntity.setPlayerName(settings.getHighScoreName());
                HighScoreEntity highScoreEntity1 = highScoreDataBase.daoAccess().fetchHighScore();
                if(highScoreEntity1 != null) highScoreDataBase.daoAccess().deleteHighScoreEntity(highScoreEntity1);
                highScoreDataBase.daoAccess () . insertScore(highScoreEntity);
                isDoneStoring = true;
            }
        }) .start();
    }
    public void showSoundPopupMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ON:

                        settings.setSound(true);
                        return true;
                    case R.id.Off:

                        settings.setSound(false);
                        return true;

                }



                return false;
            }
        });
        popup.inflate(R.menu.sound_menu);
        popup.show();
    }
    public void createHighScoreButton(){
        scoreBoardButton = (Button) findViewById(R.id.High_Score_Button);
        scoreBoardButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        scoreBoardButton.setVisibility(View.INVISIBLE);
                        Log.i("i","text is"+settings.getHighScoreName());
                        Log.i("A","score is"+settings.getHighestScore());
                       if(settings.getHighScoreName()!= null){
                           Log.i("A","name is not null");
                           highScoreNameView.setText(settings.getHighScoreName());
                           Log.i("A","name is"+settings.getHighScoreName()+"using settings interface");
                       }
                        highestScoreView.setText(Integer.toString(settings.getHighestScore()));

                        highestScoreView.setVisibility(View.VISIBLE);
                        highScoreNameView.setVisibility(View.VISIBLE);
                        highScoresTitle.setVisibility(View.VISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:
                        scoreBoardButton.setVisibility(View.VISIBLE);
                        highestScoreView.setVisibility(View.INVISIBLE);
                        highScoreNameView.setVisibility(View.INVISIBLE);
                        highScoresTitle.setVisibility(View.INVISIBLE);

                    return true;


                }


                return false;
            }
        });
    }
    public void showDifficultyMenu(View v){
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Easy:

                       settings.setDifficulty(1);
                        return true;
                    case R.id.Medium:

                        settings.setDifficulty(2);
                        return true;
                    case R.id.Hard:

                        settings.setDifficulty(3);
                        return true;

                }



                return false;
            }
        });
        popup.inflate(R.menu.difficulty_menu);
        popup.show();
    }
    public int getDifficulty(){
        return difficulty;
    }


    public void getHighScore() {

        // retrieves high score from database
        // only one high score persists at any given time.
        //query is made to retrieve all high scores. but insert operation
        // also deletes all entities
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isDoneStoring == false);
               // Log.i("A","done storing");
                HighScoreEntity highScoreEntity =  highScoreDataBase.daoAccess () .fetchHighScore();
                if(highScoreEntity == null){
                    //Log.i("A","highscoreentity is null");
                }
                if(highScoreEntity != null){
                settings.setHighScoreName(highScoreEntity.getPlayerName());
                settings.setHighestScore(highScoreEntity.getScore());
                }
            }
        }) .start();
    }
    public void playGame(View v){
        Intent intent = new Intent(this, PlayScore.class);
        intent.putExtra("Settings_Extra",settings);

        startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
            Log.i("A","high score retireved on activity result");
            Log.i("A"," settings before retrieval is "+ settings);
            settings = (Settings) data.getSerializableExtra("Settings_Extra");
        Log.i("A"," settings after retrieval is "+ settings);
            if(settings.isNewHighScore() )getHighScore();

    }
}
