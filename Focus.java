import util.Matrix;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Focus {
    Vector location;
    Vector dir;
    Vector up;
    Vector right;

    Matrix rotation = Matrix.constructByXYRotation(Math.PI/400);

    public Focus(){
        location = new Vector(0,0,0);
        dir = new Vector(0,1,0);
        right = new Vector(1,0,0);
        up = right.cross(dir);
    }

    public Iterable<SuperTriangle> projectThrough(Shape3D shape){
        ArrayList<SuperTriangle> list = new ArrayList<>();
        Vector[] vectors = new Vector[3];
        apple: for(SuperTriangle triangle: shape){
            if(triangle.center().sub(location).dot(triangle.getNormal())<=0){
                Vector[] points = triangle.getPoints();
                for(int i = 0; i<3; i++){
                    double xlen = points[i].projectionLengthOn(right);
                    double zlen = points[i].projectionLengthOn(up);
                    double ylen = points[i].projectionLengthOn(dir);
                    if(ylen<0){
                        break apple;
                    }
                    double theta = Math.atan(xlen/ylen);
                    double phi = Math.atan(zlen/Math.sqrt(ylen*ylen+xlen*xlen));
                    vectors[i] = new Vector(GTest.DEFAULT_WIDTH/2 + (int)((xlen/ylen)*GTest.DEFAULT_WIDTH/2),
                                                GTest.DEFAULT_HEIGHT/2 - (int)((zlen/ylen)*GTest.DEFAULT_HEIGHT/2), 0);
                }
                SuperTriangle triangle1 = new SuperTriangle(vectors);
                triangle1.setColor(triangle.getColor());
                list.add(triangle1);
            }
        }
        return list;
    }

    public void turn(){
        dir = rotation.leftMultiply(dir);
        up = rotation.leftMultiply(up);
        right = rotation.leftMultiply(right);
    }
}
