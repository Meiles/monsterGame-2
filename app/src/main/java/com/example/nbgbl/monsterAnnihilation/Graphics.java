package com.example.nbgbl.monsterAnnihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class Graphics extends SurfaceView  {


    private int numberOfPowerUps;
    private int numberOfObstacles;
    private int numberOfGuns;
    private int numberOfBullets;
    private int numberOfMonsters;
    private MonsPlayer player;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;
    private AI ai;
    private PlayScore playScore;
    private ArrayList<Bomb> bombs;
    private ArrayList<Bullet> bullets;
    private ArrayList<Monster> monsters;
    private ArrayList<Guns> guns;
    private ArrayList<Obstacle> obstacles;
    private int bulletId;
    private ArrayList<PowerUp> powerUps;
    private Bitmap bombBitmap;
    private  Bitmap bulletBitmap;
    private  Bitmap monsterBitmap;
    private Bitmap gunBitmap;
    private Bitmap obstacleBitmap;
    private Bitmap powerUpBitmap;
    private Bitmap playerBitmap;
    private int id;
    private int width;
    private int height;


    public Graphics(Context context, PlayScore playScore){

        super(context);
        this.setId(R.id.graphicsView);
        this.setPlayScore(playScore);
        surfaceHolder = getHolder();
        paint = new Paint();
        initActors();
        id= 0;
        DisplayMetrics dm = new DisplayMetrics();
        playScore.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
         width = dm.widthPixels;
         setHeight(dm.heightPixels);
        initBitmaps();
        bulletId = 0;


    }
    public void initActors(){

        setBullets(new ArrayList<Bullet>());
        setGuns(new ArrayList<Guns>());
        setMonsters(new ArrayList<Monster>());
        setObstacles(new ArrayList<Obstacle>());
        setPowerUps(new ArrayList<PowerUp>());
        setPlayer(new MonsPlayer(getContext(),this));
        setBombs(new ArrayList<Bomb>());

    }
    public void launchBomb(){
        getBombs().add(new Bomb(getContext(),this));

    }
    public void initBitmaps(){
       setBombBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.bomb));
       setBombBitmap(Bitmap.createScaledBitmap(getBombBitmap(),250,250,false));
       setBulletBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.bullet2));
        setBulletBitmap(Bitmap.createScaledBitmap(getBulletBitmap(),150,150,false));

        setGunBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.gun));
        setGunBitmap(Bitmap.createScaledBitmap(getGunBitmap(),250,250,false));

        setPlayerBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.player2));
        setPlayerBitmap(Bitmap.createScaledBitmap(getPlayerBitmap(),150,150,false));

        setObstacleBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.obstacle));
        setObstacleBitmap(Bitmap.createScaledBitmap(getObstacleBitmap(),250,250,false));

        setMonsterBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.monster));
        setMonsterBitmap(Bitmap.createScaledBitmap(getMonsterBitmap(),250,250,false));

        setPowerUpBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.obstacle));
        setPowerUpBitmap(Bitmap.createScaledBitmap(getPowerUpBitmap(),250,250,false));


    }
    public void shootBullet(){
        getBullets().add(new Bullet(getContext(),this));

    }



    public void createMonster(int id){
        getMonsters().add(new Monster(getContext(),this, id));
        getMonsters().get(id).setPosition(new Vector3d(getpixelWidth()* Math.random(), 0, 0));
    }
    public void createObstacle(){
        getObstacles().add(new Obstacle(getContext(),this));
        getObstacles().get(id).setPosition(new Vector3d(getpixelWidth()* Math.random(), 0, 0));
    }
    public  void increaseMonstersLevels(){
        for(int i = 0; i < monsters.size();i ++){
           monsters.get(i).increaseMonsterLevel();
        }
    }
    public  void increasePowUpLevel(){
        for(int i = 0; i < powerUps.size();i ++){
            powerUps.get(i).increaseLevel();
        }
    }
    public  void killAllMonsters(){
        for(int i = 0; i < monsters.size();i ++){
            monsters.remove(i);
        }
    }
    public void createPowerUp(){
        getPowerUps().add(new PowerUp(getContext(),this));
        getPowerUps().get(id).setPosition(new Vector3d(getpixelWidth()* Math.random(), 0, 0));
    }

    public void update(){
    // loops through arraylist of actors, updating each
       // Log.i("D", "graphics update function called");


    updateActors();
    }
    public void draw(){

        if (surfaceHolder.getSurface().isValid()) {
           // Log.i("D","surface holder is valid");
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);// white background

            paint.setColor(Color.BLACK);// color of lives and score
            paint.setTextSize(80);
            paint.setTextAlign(Paint.Align.CENTER);

            int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
            canvas.drawText("Score: " + getPlayScore().getScore(),canvas.getWidth()/2,yPos-980,paint);
            canvas.drawText("Lives: " + ai.getNumberOfLives(),canvas.getWidth()/2-500,yPos-980,paint);
            // draws current score and lives
            drawActors(canvas,paint);


            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    // loops through array list of actors, drawing each
    }
    public void drawActors(Canvas canvas, Paint paint){
        for(int i = 0; i < getBombs().size(); i ++){
            getBombs().get(i).draw(canvas,paint, bombBitmap);
        }
        for(int i = 0; i < getBullets().size(); i ++){
            getBullets().get(i).draw(canvas,paint,bulletBitmap );
        }
        for(int i = 0; i < getGuns().size(); i ++){
            getGuns().get(i).draw(canvas,paint,gunBitmap);
        }
        for(int i = 0; i < getMonsters().size(); i ++){
            getMonsters().get(i).draw(canvas,paint,monsterBitmap);
        }
        for(int i = 0; i < getObstacles().size(); i ++){
            getObstacles().get(i).draw(canvas,paint,obstacleBitmap);
        }
        for(int i = 0; i < getPowerUps().size(); i ++){
            getPowerUps().get(i).draw(canvas,paint,powerUpBitmap);
        }
        getPlayer().draw(canvas,paint, playerBitmap);
    }
    public  void updateActors(){
        for(int i = 0; i < getBombs().size(); i ++){
            getBombs().get(i).update();
            if(getBombs().get(i).isLeaving())getBombs().remove(i);
        }
        for(int i = 0; i < getBullets().size(); i ++){
            getBullets().get(i).update();
            if(getBullets().get(i).isLeaving())getBullets().remove(i);
        }
        for(int i = 0; i < getGuns().size(); i ++){
            getGuns().get(i).update();
            if(getGuns().get(i).isLeaving())getGuns().remove(i);
        }
        for(int i = 0; i < getMonsters().size(); i ++){
            getMonsters().get(i).update();
            if(getMonsters().get(i).isLeaving())getMonsters().remove(i);
        }
        for(int i = 0; i < getObstacles().size(); i ++){
            getObstacles().get(i).update();
            if(getObstacles().get(i).isLeaving())getObstacles().remove(i);
        }
        for(int i = 0; i < getPowerUps().size(); i ++){
            getPowerUps().get(i).update();
            if(getPowerUps().get(i).isLeaving())getPowerUps().remove(i);
        }
        player.update();
    }
    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Guns> getGuns() {
        return guns;
    }

    public void setGuns(ArrayList<Guns> guns) {
        this.guns = guns;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public MonsPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MonsPlayer player) {
        this.player = player;
    }

    public int getpixelWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getpixelHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PlayScore getPlayScore() {
        return playScore;
    }

    public void setPlayScore(PlayScore playScore) {
        this.playScore = playScore;
    }

    public Bitmap getBombBitmap() {
        return bombBitmap;
    }

    public void setBombBitmap(Bitmap bombBitmap) {
        this.bombBitmap = bombBitmap;
    }

    public Bitmap getBulletBitmap() {
        return bulletBitmap;
    }

    public void setBulletBitmap(Bitmap bulletBitmap) {
        this.bulletBitmap = bulletBitmap;
    }

    public Bitmap getMonsterBitmap() {
        return monsterBitmap;
    }

    public void setMonsterBitmap(Bitmap monsterBitmap) {
        this.monsterBitmap = monsterBitmap;
    }

    public Bitmap getGunBitmap() {
        return gunBitmap;
    }

    public void setGunBitmap(Bitmap gunBitmap) {
        this.gunBitmap = gunBitmap;
    }

    public Bitmap getObstacleBitmap() {
        return obstacleBitmap;
    }

    public void setObstacleBitmap(Bitmap obstacleBitmap) {
        this.obstacleBitmap = obstacleBitmap;
    }

    public Bitmap getPowerUpBitmap() {
        return powerUpBitmap;
    }

    public void setPowerUpBitmap(Bitmap powerUpBitmap) {
        this.powerUpBitmap = powerUpBitmap;
    }

    public Bitmap getPlayerBitmap() {
        return playerBitmap;
    }

    public void setPlayerBitmap(Bitmap playerBitmap) {
        this.playerBitmap = playerBitmap;
    }
}
