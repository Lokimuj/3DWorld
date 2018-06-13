package util;

import java.util.Arrays;

public class Vector {
    private double[] vector;
    private int dimensions;
    public Vector(double... args){
        dimensions = args.length;
        if(dimensions <2){
            throw new LinearAlgebraException("Vectors must be at least 2 long");
        }
        vector = Arrays.copyOf(args, dimensions);
    }
    public Vector(int dimensions) throws LinearAlgebraException {
        if(dimensions < 2){
            throw new LinearAlgebraException("Vectors must be at least 2 long");
        }
        vector = new double[dimensions];
        this.dimensions = dimensions;
    }
    public Vector(Vector other){
        this.dimensions = other.dimensions;
        this.vector = Arrays.copyOf(other.vector,other.dimensions);
    }

    public double getX() {

        return vector[0];
    }

    public double getY() {

        return vector[1];
    }

    public double getZ(){
        if(dimensions < 3){
            throw new LinearAlgebraException("Vector must have at least 3 dimensions to have a Z coordinate");
        }
        return vector[2];
    }

    public double getIndex(int i){
        if(i >= dimensions){
            throw new LinearAlgebraException("Tried to access index "+i+" of a vector with only "+ dimensions + "dimensions");
        }
        return vector[i];
    }

    public int getDimensions() {
        return dimensions;
    }

    public void scalarMultiplyThis(double scalar){
        for(int i = 0; i< dimensions; i++){
            vector[i]*=scalar;
        }
    }

    public Vector scalarMultiple(double scalar){
        Vector result = new Vector(this);
        result.scalarMultiplyThis(scalar);
        return result;
    }

    public double dot(Vector other){
        if(other.dimensions != this.dimensions){
            throw new LinearAlgebraException("Vectors must have the same dimensions to be dotted. This: "+this.dimensions +", other: "+other.dimensions);
        }
        double sum = 0;
        for(int i = 0; i< dimensions; i++){
            sum+=this.vector[i]*other.vector[i];
        }
        return sum;
    }
    public Vector cross(Vector other){
        if(this.dimensions != 3 || other.dimensions != 3){
            throw new LinearAlgebraException("Vectors must both be of dimensions 3 to be crossed. This: "+this.dimensions +", other: "+other.dimensions);
        }
        return new Vector(this.vector[1]*other.vector[2]-this.vector[2]*other.vector[1],
                            -1*(this.vector[0]*other.vector[2]-this.vector[2]*other.vector[1]),
                            this.vector[0]*other.vector[1]-this.vector[1]*other.vector[0]);
    }
    public void addToThis(Vector other){
        if(other.dimensions != this.dimensions){
            throw new LinearAlgebraException("Vectors must have the same dimensions to be added. This: "+this.dimensions +", other: "+other.dimensions);
        }
        for(int i = 0; i < dimensions; i++){
            this.vector[i]+=other.vector[i];
        }
    }
    public Vector add(Vector other){
        if(other.dimensions != this.dimensions){
            throw new LinearAlgebraException("Vectors must have the same dimensions to be added. This: "+this.dimensions +", other: "+other.dimensions);
        }
        Vector result = new Vector(this);
        result.addToThis(other);
        return result;
    }
    public void subFromThis(Vector other){
        if(other.dimensions != this.dimensions){
            throw new LinearAlgebraException("Vectors must have the same dimensions to be subtracted. This: "+this.dimensions +", other: "+other.dimensions);
        }
        Vector temp = other.scalarMultiple(-1);
        this.addToThis(temp);
    }
    public Vector sub(Vector other){
        if(other.dimensions != this.dimensions){
            throw new LinearAlgebraException("Vectors must have the same dimensions to be subtracted. This: "+this.dimensions +", other: "+other.dimensions);
        }
        Vector result = new Vector(this);
        result.subFromThis(other);
        return result;
    }
    public Vector unit(){
        return this.scalarMultiple(1/magnitude());
    }

    public double magnitude(){
        double sum = 0;
        for(int i = 0; i < dimensions; i++){
            sum += vector[i]*vector[i];
        }
        return Math.sqrt(sum);
    }

    public double angle(Vector other){
        if(other.dimensions != this.dimensions){
            throw new LinearAlgebraException("Vectors must have the same dimensions to be added. This: "+this.dimensions +", other: "+other.dimensions);
        }
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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector)) {
            return false;
        }
        Vector real = (Vector) other;
        return this.dimensions == real.dimensions && Arrays.equals(this.vector, real.vector);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder("Vector{");
        for(int i = 0; i<dimensions; i++){
            resultBuilder.append("| ").append(vector[i]).append(" |");
        }
        String result = resultBuilder.toString();
        result+="}";
        return result;
    }
}
