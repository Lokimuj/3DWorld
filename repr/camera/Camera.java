package repr.camera;

import repr.shapes.SuperTriangle;
import util.Matrix;
import util.Vector;

public abstract class Camera {

    protected Vector position;
    protected Vector face;

    public Camera(){}

    public Camera(Vector position, Vector face){
        this.position = position;
        this.face = face;
    }

    public void turn(Matrix rotation){
        face = rotation.leftMultiply(face);
    }


    public void translate(Vector d){
        position.addToThis(d);
    }

    public abstract Iterable<SuperTriangle> view(Iterable<SuperTriangle> triangles);
}
