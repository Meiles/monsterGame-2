package com.example.nbgbl.monsterAnnihilation;

public class Vector3d {
    private double[] coordinates = new double[3];
    public Vector3d(double x, double y, double z){
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }
    public Vector3d(Vector3d vec){
        coordinates[0] = vec.GetX();
        coordinates[1] = vec.GetY();
        coordinates[2] = vec.GetY();
    }
    public void reset(){
        coordinates[0] = 0;
        coordinates[1] = 0;
        coordinates[2] = 0;
    }
    public Vector3d(double coords[]){
        coordinates[0] = coords[0];
        coordinates[1] = coords[1];
        coordinates[2] = coords[2];
    }
    public void SetX(double x){
        coordinates[0] = x;
    }
    public void SetY(double y){
        coordinates[1] = y;
    }
    public void SetZ(double z){
        coordinates[2] = z;
    }
    public  double GetX(){
        return coordinates[0];
    }
    public double GetY(){
        return coordinates[1];
    }
    public double GetZ(){
        return coordinates[2];
    }
    public void SetAll(double x, double y, double z){
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }
    public Vector3d plus(Vector3d tmp){

        return new Vector3d(coordinates[0] + tmp.GetX(),
                coordinates[1] + tmp.GetY(),
                coordinates[2] + tmp.GetZ() );
    }
    public double dotProductWith(Vector3d tmp){
        return coordinates[0]*tmp.GetX()+coordinates[1]*tmp.GetY()+coordinates[2]*tmp.GetZ();
    }
    public Vector3d minus(Vector3d tmp){

        return new Vector3d(coordinates[0] - tmp.GetX(),
                coordinates[1] - tmp.GetY(),
                coordinates[2] - tmp.GetZ() );
    }
    public double norm(){
        return Math.sqrt(this.normsqr());
    }
    public Vector3d selfNormalize() {
        double n = this.norm();
        Vector3d newvector = new Vector3d(0,0,0);
        if(n < Math.ulp(1))
            return newvector;
        return this.divideEquals(n);
    }
    Vector3d normalize()  {
        double n = norm();
        Vector3d newvector = new Vector3d(this.GetX(),this.GetY(),this.GetZ());
        if(n < Math.ulp(1)) return newvector;
        return this.divideScalar(n);
    }
    public double normsqr(){
        return this.dotProductWith(this);
    }
    public Vector3d multiplyEquals(double scalar){
        coordinates[0] *= scalar;
        coordinates[1] *= scalar;
        coordinates[2] *= scalar;
        return this;
    }
    public Vector3d selfScale(double L) {
        double n = norm();
        Vector3d newvector = new Vector3d(0,0,0);
        if(n < Math.ulp(1)) return newvector;
        return this.multiplyEquals(L/n);
    }
    public Vector3d divideEquals(double scalar){
        coordinates[0] /= scalar;
        coordinates[1] /= scalar;
        coordinates[2] /= scalar;
        return this;
    }
    public Vector3d divideScalar(double scalar) {
        return new Vector3d( coordinates[0]/scalar,coordinates[1]/scalar,coordinates[2]/scalar);
    }
    public Vector3d multipliedBy(double scalar) {
        return new Vector3d( coordinates[0]*scalar,coordinates[1]*scalar,coordinates[2]*scalar);
    }
    @Override
    public String toString(){
        return ""+ this.GetX() + " "+ this.GetY()+ " " + this.GetZ();
    }
}
