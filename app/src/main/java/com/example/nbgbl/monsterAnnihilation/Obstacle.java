package com.example.nbgbl.monsterAnnihilation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Obstacle extends Actors {

    public Obstacle(Context context,Graphics graphics){
        super(context);// sets position, velocity, force to zero

        setPosition(new Vector3d(600,200,0));
        setVelocity(new Vector3d(0,100,0));

        setGraphics(graphics);
    }
    @Override
    public void update(){
        super.update();
        wrap();



        if(isWrapped()) {
            setWrapped(false);
            setPosition(new Vector3d(getGraphics().getpixelWidth()* Math.random(), 0, 0));}

    }
    @Override()
    public void wrap(){
        super.wrap();

    }
    @Override
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        super.draw(canvas,paint, bitmap);
    }
    @Override
    public void resolveCollisions(){

    }

}
