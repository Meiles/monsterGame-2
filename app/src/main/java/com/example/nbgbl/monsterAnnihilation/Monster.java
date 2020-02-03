package com.example.nbgbl.monsterAnnihilation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Monster extends Actors{
    ArrayList<Monster> monsters;
    private int id;
    private double viewRadius;
    private double separationComponent;
    private double cohesionComponent;
    private double alignmentComponent;
    private double migratoryComponent;
    private double maxAccel;
    private double maxVel;
    private double mass;
    private int monsterHealth;
    private int monsterSpeed;
    private int level;
    public Monster(Context context,Graphics graphics, int id){

    super(context);//sets position, velocity, force to zero

        setPosition(new Vector3d(250,250,0));
        setVelocity(new Vector3d(20*(Math.random()-0.5), 20*(Math.random()-0.5),0));
        setGraphics(graphics);
        this.id = id;
        setViewRadius(400 + Math.random() * 10);
       maxAccel = 300 +Math.random()* 10;
         mass = 4 + Math.random() * 10;
         setMaxVel(100 +Math.random() * 5);
        monsters = graphics.getMonsters();
        setSeparationComponent(60);
        setCohesionComponent(60);
        setAlignmentComponent(60);
        setLevel(1);
        setMigratoryComponent(-250);
        setMonsterHealth(10);
        setMonsterSpeed(100);

    }
    @Override
    public void update()
    {
        super.update();
        double maxAdvForce = 40;
        Vector3d separation = new Vector3d(0,0,0);
        Vector3d cohesion= new Vector3d(0,0,0);
        Vector3d alignment= new Vector3d(0,0,0);
        Vector3d center= new Vector3d(0,0,0);
        Vector3d force= new Vector3d(0,0,0);
        Vector3d migrate = new Vector3d(0,0,0);

        int numSeen = 0;
        wrap();
        for(int i=0; i<(int)monsters.size(); i++) {
            if( id == i ) continue; //skip self
            //see if within view radius
            double dist = (monsters.get(i).getPosition().minus( getPosition()).norm());
            if( dist < getViewRadius()) {
               separation =separation.plus((getPosition().minus( monsters.get(i).getPosition() )).normalize().multipliedBy(1.0-dist/ getViewRadius()));
              center = center.plus( monsters.get(i).getPosition());
               alignment = alignment.plus( monsters.get(i).getVelocity());
               migrate = (getPosition().minus( getGraphics().getPlayer().getPosition())).normalize().multipliedBy(migratoryComponent);
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

          force = separation.plus( cohesion.plus( alignment.plus(migrate)));
            if( force.norm()>maxAccel ) force.selfScale(maxAccel);

            force.divideEquals( mass);

            ///////////////////////////
        }
       setAccel(force);
        if( getVelocity().norm() > getMaxVel()) {
            getVelocity() .selfScale(getMaxVel());
        }

    }
    @Override
    public  void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        super.draw(canvas,paint, bitmap);

    }
    @Override
    public void resolveCollisions(){

    }
    public  void increaseMonsterLevel(){
        setMonsterSpeed(getMonsterSpeed()*2);
        setMonsterHealth(getMonsterHealth()*2);
        setMigratoryComponent(getMigratoryComponent()*2);
        setMaxVel(getMaxVel()*2);
        setViewRadius(getViewRadius()*1.2);
        setSeparationComponent(getSeparationComponent()*1.5);
        setAlignmentComponent(getAlignmentComponent()*1.5);
        setCohesionComponent(getCohesionComponent()*.95);
    }
    public double getMigratoryComponent() {
        return migratoryComponent;
    }

    public void setMigratoryComponent(double migratoryComponent) {
        this.migratoryComponent = migratoryComponent;
    }

    public int getMonsterHealth() {
        return monsterHealth;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.monsterHealth = monsterHealth;
    }

    public int getMonsterSpeed() {
        return monsterSpeed;
    }

    public void setMonsterSpeed(int monsterSpeed) {
        this.monsterSpeed = monsterSpeed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getViewRadius() {
        return viewRadius;
    }

    public void setViewRadius(double viewRadius) {
        this.viewRadius = viewRadius;
    }

    public double getMaxVel() {
        return maxVel;
    }

    public void setMaxVel(double maxVel) {
        this.maxVel = maxVel;
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
}
