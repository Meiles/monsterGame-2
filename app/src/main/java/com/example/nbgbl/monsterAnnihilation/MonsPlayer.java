package com.example.nbgbl.monsterAnnihilation;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class MonsPlayer extends Actors{
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Bomb> bombs;
    private ArrayList<Monster> monsters;
    public MonsPlayer(Context context,Graphics graphics){
        super(context);

        setPosition(new Vector3d(500,1200,0));
        setGraphics(graphics);
        monsters = graphics.getMonsters();
        powerUps = graphics.getPowerUps();
    }
    @Override
    public void update(){
        super.update();
        wrap();
        for(int i = 0; i < monsters.size();i ++){
            double distance = (this.getPosition().minus(monsters.get(i).getPosition())).norm();
            double totalRadius = getGraphics().getPlayerBitmap().getWidth()+getGraphics().getMonsterBitmap().getWidth();
            if(distance < totalRadius) monsters.get(i).setLeaving(true);
        }
        for(int i = 0; i < powerUps.size();i ++){
            double distance = (this.getPosition().minus(powerUps.get(i).getPosition())).norm();
            double totalRadius = getGraphics().getPlayerBitmap().getWidth()+getGraphics().getPowerUpBitmap().getWidth();
            if(distance < totalRadius) {
                powerUps.get(i).setLeaving(true);
                if(powerUps.get(i).getPowerUpType() == 1) {

                  int num = getGraphics().getAi().getNumberOfPowerUps();
                  getGraphics().getAi().setNumberOfPowerUps(num+1);

                }
                if(powerUps.get(i).getPowerUpType() == 2) {

                    int num = getGraphics().getAi().getNumberOfLives();
                    getGraphics().getAi().setNumberOfLives(num+1);

                }
                if(powerUps.get(i).getPowerUpType() == 3) {

                    int num = getGraphics().getAi().getNumberOfPowerUps();
                    getGraphics().getAi().setNumberOfPowerUps(num+1);

                }

            }
        }
    }
    @Override
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        Vector3d gunDirect = getGraphics().getPlayScore().getGunDirection();
        float angleRotate = (float) Math.atan2(gunDirect.GetY(),gunDirect.GetX())*180/(float)Math.PI-90;
        Matrix matrix = new Matrix();
        matrix.setRotate(angleRotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 150, 150, matrix, true);
        matrix.setRotate(-angleRotate);
        matrix.setTranslate((float) getPosition().GetX(),(float) getPosition().GetY());
        canvas.drawBitmap(rotatedBitmap,matrix,paint);
    }
    @Override
    public void resolveCollisions(){

    }

    public void shoot(){

    }
    public void useBomb(){

    }
}
