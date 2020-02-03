package com.example.nbgbl.monsterAnnihilation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class Guns extends Actors {
    public  Guns(Context context,Graphics graphics){


        super(context);//sets position, velocity, force to zero

        setPosition(new Vector3d(500,300,0));
        setGraphics(graphics);
    }
    @Override
    public void update(){
        super.update();


    }
    @Override
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        Vector3d gunDirect = getGraphics().getPlayScore().getGunDirection();
        float angleRotate = (float) Math.atan2(gunDirect.GetY(),gunDirect.GetX())*180/(float)Math.PI;
        Matrix matrix = new Matrix();
        matrix.setRotate(angleRotate);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 200, 200, matrix, true);
        matrix.setRotate(-angleRotate);
        matrix.setTranslate((float) getPosition().GetX(),(float) getPosition().GetY());
        canvas.drawBitmap(rotatedBitmap,matrix,paint);

    }
    @Override
    public void resolveCollisions(){

    }

}
