package util;

import javax.sound.sampled.Line;
import java.util.Arrays;
import java.util.Objects;

public class Vector {
    private double[] vector;
    private int length;
    public Vector(double... args){
        length = args.length;
        if(length<2){
            throw new LinearAlgebraException("Vectors must be at least 2 long");
        }
        vector = Arrays.copyOf(args,length);
    }
    public Vector(int length) throws LinearAlgebraException {
        if(length<2){
            throw new LinearAlgebraException("Vectors must be at least 2 long");
        }
        vector = new double[length];
        this.length = length;
    }
    public Vector(Vector other){
        this.length = other.length;
        this.vector = Arrays.copyOf(other.vector,other.length);
    }

    public double getX() {

        return vector[0];
    }

    public double getY() {

        return vector[1];
    }

    public double getZ(){
        if(length<3){
            throw new LinearAlgebraException("Vector must be at least 3 long to have a Z coordinate");
        }
        return vector[2];
    }

    public double getIndex(int i){
        if(i>=length){
            throw new LinearAlgebraException("Tried to access index "+i+" of a vector only "+ length + "long");
        }
        return vector[i];
    }

    public void scalarMultiplyThis(double scalar){
        for(int i = 0;i<length;i++){
            vector[i]*=scalar;
        }
    }

    public Vector scalarMultiple(double scalar){
        Vector result = new Vector(this);
        result.scalarMultiplyThis(scalar);
        return result;
    }

    public double dot(Vector other){
        if(other.length!=this.length){
            throw new LinearAlgebraException("Vectors must be same length to be dotted. This: "+this.length+", other: "+other.length);
        }
        double sum = 0;
        for(int i = 0; i<length; i++){
            sum+=this.vector[i]*other.vector[i];
        }
        return sum;
    }
    public Vector cross(Vector other){
        if(this.length!=3 || other.length!=3){
            throw new LinearAlgebraException("Vectors must both be of length 3 to be crossed. This: "+this.length+", other: "+other.length);
        }
        return new Vector(this.vector[1]*other.vector[2]-this.vector[2]*other.vector[1],
                            -1*(this.vector[0]*other.vector[2]-this.vector[2]*other.vector[1]),
                            this.vector[0]*other.vector[1]-this.vector[1]*other.vector[0]);
    }
    public void addToThis(Vector other){
        if(other.length!=this.length){
            throw new LinearAlgebraException("Vectors must be same length to be added. This: "+this.length+", other: "+other.length);
        }
        for(int i = 0; i<length;i++){
            this.vector[i]+=other.vector[i];
        }
    }
    public Vector add(Vector other){
        Vector result = new Vector(this);
        result.addToThis(other);
        return result;
    }
    public void subFromThis(Vector other){
        Vector temp = other.scalarMultiple(-1);
        this.addToThis(temp);
    }
    public Vector sub(Vector other){
        Vector result = new Vector(this);
        result.subFromThis(other);
        return result;
    }
    public Vector unit(){
        return this.scalarMultiple(1/magnitude());
    }
    public double magnitude(){
        double sum = 0;
        for(int i = 0; i<length; i++){
            sum+=vector[i]*vector[i];
        }
        return Math.sqrt(sum);
    }

    public double angle(Vector other){
        return Math.acos(this.dot(other)/(this.magnitude()*other.magnitude()));
    }

    public double projectionLengthOn(Vector other){
        return this.dot(other)/other.magnitude();
    }

    public Vector projectOnto(Vector other){
        Vector result = other.unit();
        result.scalarMultiplyThis(this.projectionLengthOn(other));
        return result;
    }

    public double distanceTo(Vector other){
        return other.sub(this).magnitude();
    }

    public boolean equals(Object other){
        if(!(other instanceof Vector)){
            return false;
        }
        Vector real = (Vector) other;
        if(this.length!= real.length){
            return false;
        }
        return Arrays.equals(this.vector,real.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}
