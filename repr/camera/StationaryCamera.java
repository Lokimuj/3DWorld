package repr.camera;

import util.Vector;

public class StationaryCamera extends CameraDecorator {

    public StationaryCamera(Camera camera){
        super(camera);
    }

    @Override
    public void translate(Vector d) {}
}
