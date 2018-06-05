package util;

public class Vector {
    private final double x;
    private final double y;
    private final double z;

    public Vector(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector(){
        this(0,0,0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector multiple(double scalar){
        return new Vector(x*scalar,y*scalar,z*scalar);
    }
    public double dot(Vector other){
        return this.x*other.x + this.y*other.y + this.z+other.z;
    }
    public Vector cross(Vector other){
        return new Vector(this.y*other.z-this.z*other.y, -1*(this.x*other.z-this.z*other.x),this.x*other.y-this.y*other.x);
    }
    public Vector add(Vector other){
        return new Vector(this.x+other.x,this.y+other.y,this.z+other.z);
    }
    public Vector sub(Vector other){
        return new Vector(this.x-other.x,this.y-other.y,this.z-other.z);
    }
    public Vector unit(){
        double magnitude = magnitude();
        return new Vector(x/magnitude,y/magnitude,z/magnitude);
    }
    public double magnitude(){
        return Math.pow(x*x+y*y+z*z,0.5);
    }
    public double theta(){
        return Math.atan(y/x);
    }
    public double phi(){
        return Math.PI/2 - Math.asin(z/magnitude());
    }
}
