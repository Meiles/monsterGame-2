package com.example.nbgbl.monsterAnnihilation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class PowerUp extends  Actors {
    private int powerUpType;
    private String  powerUpName;
    private ArrayList<Monster> monsters;

    private double viewRadius;
    private double separationComponent;
    private double cohesionComponent;
    private double alignmentComponent;

    private double maxAccel;
    private double maxVel;
    private double mass;

    private int Speed;

    public PowerUp(Context context,Graphics graphics){


        super(context);

        setPosition(new Vector3d(100,800,0));
        setGraphics(graphics);
        setPowerUpType(0);
        getRandomPowerUp();
        setViewRadius(400 + Math.random() * 10);
        maxAccel = 300 +Math.random()* 10;
        mass = 4 + Math.random() * 10;
        setMaxVel(100 +Math.random() * 5);
        monsters = graphics.getMonsters();
        setSeparationComponent(60);
        setCohesionComponent(60);
        setAlignmentComponent(60);


    }
    @Override
    public void update(){
        super.update();
        double maxAdvForce = 40;
        Vector3d separation = new Vector3d(0,0,0);
        Vector3d cohesion= new Vector3d(0,0,0);
        Vector3d alignment= new Vector3d(0,0,0);
        Vector3d center= new Vector3d(0,0,0);
        Vector3d force= new Vector3d(0,0,0);

        int numSeen = 0;
        wrap();
        for(int i = 0; i<(int) getMonsters().size(); i++) {

            //see if within view radius
            double dist = (getMonsters().get(i).getPosition().minus( getPosition()).norm());
            if( dist < getViewRadius()) {
                separation =separation.plus((getPosition().minus( getMonsters().get(i).getPosition() )).normalize().multipliedBy(1.0-dist/ getViewRadius()));
                center = center.plus( getMonsters().get(i).getPosition());
                alignment = alignment.plus( getMonsters().get(i).getVelocity());
                numSeen++;
            }
        }//endfor i

        if( numSeen>0 ) {
            //////////////////////////
            // only done if can see other monsters
            //cout << "num seen > 0" << endl;
            center.divideEquals( 1.0*numSeen);
            double distToCenter = (center.minus( getPosition())).norm();
            cohesion = (center.minus( getPosition())).normalize().multipliedBy( getCohesionComponent() *(distToCenter/ getViewRadius()));
            separation = separation.multipliedBy(getSeparationComponent());
            alignment = alignment.normalize().multipliedBy(getAlignmentComponent());

            force = separation.plus( cohesion.plus( alignment));
            if( force.norm()> getMaxAccel()) force.selfScale(getMaxAccel());

            force.divideEquals( mass);

            ///////////////////////////
        }
        setAccel(force);
        if( getVelocity().norm() > getMaxVel()) {
            getVelocity() .selfScale(getMaxVel());
        }



    }
    @Override
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        super.draw(canvas,paint, bitmap);
    }
    @Override
    public void resolveCollisions(){

    }
    public  void getRandomPowerUp(){
        double randomInt = Math.random()*3;
        if(randomInt < 1) powerUpType = 1;
        if(randomInt < 2 && randomInt >1) powerUpType = 2;
        if(randomInt < 3 && randomInt >2) powerUpType = 3;
        if(powerUpType == 1) powerUpName = "Score Boost";
        if(powerUpType == 2) powerUpName = "Wipe";
        if(powerUpType == 3) powerUpName = "Extra Life";
    }
    public  void increaseLevel(){

        setMaxVel(getMaxVel()*2);
        setViewRadius(getViewRadius()*1.2);
        setSeparationComponent(getSeparationComponent()*1.5);
        setAlignmentComponent(getAlignmentComponent()*1.5);
        setCohesionComponent(getCohesionComponent()*.95);
    }
    public int getPowerUpType() {
        return powerUpType;
    }

    public void setPowerUpType(int powerUpType) {
        this.powerUpType = powerUpType;
    }

    public String getPowerUpName() {
        return powerUpName;
    }

    public void setPowerUpName(String powerUpName) {
        this.powerUpName = powerUpName;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }



    public double getViewRadius() {
        return viewRadius;
    }

    public void setViewRadius(double viewRadius) {
        this.viewRadius = viewRadius;
    }

    public double getSeparationComponent() {
        return separationComponent;
    }

    public void setSeparationComponent(double separationComponent) {
        this.separationComponent = separationComponent;
    }

    public double getCohesionComponent() {
        return cohesionComponent;
    }

    public void setCohesionComponent(double cohesionComponent) {
        this.cohesionComponent = cohesionComponent;
    }

    public double getAlignmentComponent() {
        return alignmentComponent;
    }

    public void setAlignmentComponent(double alignmentComponent) {
        this.alignmentComponent = alignmentComponent;
    }



    public double getMaxAccel() {
        return maxAccel;
    }

    public void setMaxAccel(double maxAccel) {
        this.maxAccel = maxAccel;
    }

    public double getMaxVel() {
        return maxVel;
    }

    public void setMaxVel(double maxVel) {
        this.maxVel = maxVel;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }
}
