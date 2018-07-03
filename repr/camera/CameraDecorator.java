package repr.camera;

import repr.shapes.SuperTriangle;
import util.Matrix;
import util.Vector;

public abstract class CameraDecorator extends Camera{
    private Camera camera;
    public CameraDecorator(Camera camera){
        this.camera = camera;
    }

    @Override
    public void translate(Vector d) {
        camera.translate(d);
    }

    @Override
    public void turn(Matrix rotation) {
        camera.turn(rotation);
    }

    @Override
    public Iterable<SuperTriangle> view(Iterable<SuperTriangle> triangles) {
        return camera.view(triangles);
    }

    public Camera getCamera() {
        return camera;
    }
}
