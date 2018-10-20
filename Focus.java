import util.Matrix;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Focus {
    public static final double MAX_THETA = 55* Math.PI/180; //Max horizontal view angle in radians
    public static final double MAX_PHI = 50* Math.PI/180;   //Max vertical view angle in radians
    Vector location; //Where in the world the camera is located
    Vector dir;      // Direction this camera is facing
    Vector up;
    Vector right;

    Matrix rotation = Matrix.constructByXYRotation(0); //How much the camera will turn each frame

    public Focus(){
        location = new Vector(0,0,0);
        dir = new Vector(0,1,0); //Pointing in the Y-direction to start
        right = new Vector(1,0,0); // Indicates what 'right' is
        up = right.cross(dir);          //eases calculations
    }

    /**
     * This produces a collection of triangles that can be directly drawn onto the canvas
     * Does the projection of the 3D shape onto a 'plane' using triangles going through a focus
     * @param shape the 3D shape to be drawn. Right now it will be a tetrahedron, but any shape that has every point
     *              used by triangles should be rendered fine.
     * @return The triangles relative to canvas coordinates that, when drawn, should look like the 3D shape
     */
    public Iterable<SuperTriangle> projectThrough(Shape3D shape){
        ArrayList<SuperTriangle> list = new ArrayList<>();
        Vector[] vectors = new Vector[3];
        apple: for(SuperTriangle triangle: shape){
            //Checks if the triangle is facing away from the camera, which in my code's case means it's on the other
            //side of the shape and is thus not visible
            if(triangle.center().sub(location).dot(triangle.getNormal())<=0){
                Vector[] points = triangle.getPoints();
                for(int i = 0; i<3; i++){
                    //The distances along this camera's axes so we know how far 'forward, above, and to the right' each
                    //point is relative to where we're looking
                    double xlen = points[i].projectionLengthOn(right);
                    double zlen = points[i].projectionLengthOn(up);
                    double ylen = points[i].projectionLengthOn(dir);

                    //Check to avoid triangles that have a point behind the camera, because that probably won't look pretty
                    if(ylen<0){
                        break apple;
                    }
                    double theta = Math.atan(xlen/ylen); //angle to the side that this point is from the origin relative
                                                         // to the direction this camera is facing
                    double phi = Math.atan(zlen/Math.sqrt(ylen*ylen+xlen*xlen));// vertical angle
//                    vectors[i] = new Vector(GTest.DEFAULT_WIDTH/2 + (int)((xlen/ylen)*GTest.DEFAULT_WIDTH/2),
//                                                GTest.DEFAULT_HEIGHT/2 - (int)((zlen/ylen)*GTest.DEFAULT_HEIGHT/2), 0);
                    vectors[i] = new Vector(GTest.DEFAULT_WIDTH/2 + (int)((theta/MAX_THETA)*GTest.DEFAULT_WIDTH/2),
                            GTest.DEFAULT_HEIGHT/2 - (int)((phi/MAX_PHI)*GTest.DEFAULT_HEIGHT/2), 0);
                }
                SuperTriangle triangle1 = new SuperTriangle(vectors);
                triangle1.setColor(triangle.getColor());
                list.add(triangle1);
            }
        }
        return list;
    }

    /**
     * turns the camera every frame
     */
    public void turn(){
        dir = rotation.leftMultiply(dir);
        up = rotation.leftMultiply(up);
        right = rotation.leftMultiply(right);
    }
}
