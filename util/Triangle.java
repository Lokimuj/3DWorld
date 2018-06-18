package util;

import java.util.Arrays;

public class Triangle {
    protected Vector[] points = new Vector[3];

    public Triangle(Vector... vectors ){
        if(vectors.length!=3){
            throw new IllegalArgumentException("Triangles need three points. Passed points: "+vectors.length);
        }
        for(int i = 0;i<3;i++){
            points[i] = new Vector(vectors[i]);
        }
    }

    public Vector[] getPoints() {
        Vector[] result = new Vector[3];
        for(int i = 0; i<3; i++){
            result[i] = new Vector(points[i]);
        }
        return result;
    }

    public void rotate(Matrix rotation){
        Vector center = Vector.average(points);
        for(int i = 0;i<3;i++){
            points[i] = center.add(rotation.leftMultiply(points[i].sub(center)));
        }
    }

    public void rotate(Matrix rotation, Vector origin){
        for(int i = 0;i<3;i++){
            points[i] = origin.add(rotation.leftMultiply(points[i].sub(origin)));
        }
    }
}