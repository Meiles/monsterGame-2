package com.example.nbgbl.monsterAnnihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bomb  extends Actors{
    ArrayList<Monster> monsters;
    public Bomb(Context context,Graphics graphics){
    super(context);

       setPosition(new Vector3d(graphics.getPlayer().getPosition().GetX(), graphics.getPlayer().getPosition().GetY(),0));

        setVelocity(graphics.getPlayScore().getGunDirection().multipliedBy(-1000));
        setGraphics(graphics);
        monsters = graphics.getMonsters();
    }
    @Override
    public void update(){
        super.update();
        for(int i = 0; i < monsters.size();i ++){
        double distance = (this.getPosition().minus(monsters.get(i).getPosition())).norm();
        Log.i("I","distance is"+distance);
            double totalRadius = getGraphics().getBombBitmap().getWidth()+getGraphics().getMonsterBitmap().getWidth();
            if(distance < totalRadius){

                monsters.get(i).setMonsterHealth(monsters.get(i).getMonsterHealth()-5);
                monsters.get(i).setLeaving(true);
                this.setLeaving(true);
            }

        }
        updateWrapped();
       if(isWrapped())setLeaving(true);

    }
    @Override
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
    super.draw(canvas,paint,bitmap);
    }
    @Override
    public void resolveCollisions(){

    }
}
