package com.example.nbgbl.monsterAnnihilation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Bullet extends Actors {
    ArrayList<Monster> monsters;
    public Bullet(Context context,Graphics graphics){

        super(context);// sets position, velocity, force to zero

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
            double totalRadius = getGraphics().getBulletBitmap().getWidth()+getGraphics().getMonsterBitmap().getWidth();
            if(distance < totalRadius){
                monsters.get(i).setMonsterHealth(monsters.get(i).getMonsterHealth()-1);
                if(monsters.get(i).getMonsterHealth()< 0 )monsters.get(i).setLeaving(true);
                this.setLeaving(true);
            }

        }
        updateWrapped();
        if(isWrapped())this.setLeaving(true);
    }
    @Override
     public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        Vector3d gunDirect = getGraphics().getPlayScore().getGunDirection();
        float angleRotate = (float) Math.atan2(gunDirect.GetY(),gunDirect.GetX())*180/(float)Math.PI-90;
        Matrix matrix = new Matrix();
        matrix.setRotate(angleRotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 100, 100, matrix, true);
        matrix.setRotate(-angleRotate);
        matrix.setTranslate((float) getPosition().GetX(),(float) getPosition().GetY());
        canvas.drawBitmap(rotatedBitmap,matrix,paint);
    }
    @Override
    public void resolveCollisions(){

    }

}
