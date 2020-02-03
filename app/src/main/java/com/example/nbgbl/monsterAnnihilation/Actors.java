package com.example.nbgbl.monsterAnnihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Actors {

    private double width;
    private double height;
    private boolean isLeaving;
    private Vector3d position;
   private Vector3d    velocity;
    private Vector3d accel;
    private Context context;
    private Graphics graphics;
    private boolean wrapped;
    private static final double dt = 0.02;
    private static final double damping = 0.95;
    public Actors(Context context){
        setLeaving(false);
       setPosition(new Vector3d(0,0,0));
        setAccel(new Vector3d(0,0,0));
       setVelocity(new Vector3d(0,0,0));
        this.setContext(context);
        setWrapped(false);
    }

    /*final MediaPlayer LeaveSound;
    final MediaPlayer EnterSound;
*/


    public  void update(){
        //cout << "Ball update! center = " << center << " vel = " << velocity << " accel = " << accel << endl;
        Vector3d center2 = position.plus(velocity.multipliedBy(10*dt)) ;
        position=center2;

        Vector3d velocity2 = velocity.plus(accel.multipliedBy(10*dt));
        velocity = velocity2;
        //System.out.println("velocity will be added to change in acceleration:" + accel.multipliedBy(dt));

        //System.out.println("accel after multiplied by damping is "+ accel);


        if(getAccel().norm() < 0.5 ) getAccel().SetAll(0,0,0);
        if(velocity.norm() < 0.5 ) velocity.SetAll(0,0,0);
        resolveCollisions();

    }

    public  void draw(Canvas canvas, Paint paint, Bitmap bitmap){

        canvas.drawBitmap(bitmap,(float)getPosition().GetX(),(float)getPosition().GetY(),paint);

    }
    public void resolveCollisions(){

    }
    public  void updateWrapped(){
        if (this.getPosition().GetY()> getGraphics().getpixelHeight()) {

            setWrapped(true);

        }
        if (this.getPosition().GetY()< 0) {

            setWrapped(true);

        }
        if (this.getPosition().GetX()> getGraphics().getpixelWidth()) {

            setWrapped(true);
        }
        if (this.getPosition().GetX()<0) {

            setWrapped(true);
        }
    }
    public void wrap(){
        if (this.getPosition().GetY()> getGraphics().getpixelHeight()) {
            double x = this.getPosition().GetX();
            setPosition(new Vector3d(x,0,0));
            setWrapped(true);

        }
        if (this.getPosition().GetY()< 0) {
            double x = this.getPosition().GetX();
            setPosition(new Vector3d(x,getGraphics().getpixelHeight(),0));
            setWrapped(true);

        }
        if (this.getPosition().GetX()> getGraphics().getpixelWidth()) {
            double y = this.getPosition().GetY();
            setPosition(new Vector3d(0,y,0));
        setWrapped(true);
        }
        if (this.getPosition().GetX()<0) {
            double y = this.getPosition().GetY();
            setPosition(new Vector3d(getGraphics().getpixelWidth(),y,0));
            setWrapped(true);
        }
    }
    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity;
    }


    public Vector3d getAccel() {
        return accel;
    }

    public void setAccel(Vector3d accel) {
        this.accel = accel;
    }




    public Graphics getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public boolean isLeaving() {
        return isLeaving;
    }

    public void setLeaving(boolean leaving) {
        isLeaving = leaving;
    }

    public boolean isWrapped() {
        return wrapped;
    }

    public void setWrapped(boolean wrapped) {
        this.wrapped = wrapped;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
