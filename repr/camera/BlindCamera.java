package repr.camera;

import repr.shapes.SuperTriangle;

import java.util.ArrayList;

public class BlindCamera extends CameraDecorator {

    public BlindCamera(Camera camera){
        super(camera);
    }

    @Override
    public Iterable<SuperTriangle> view(Iterable<SuperTriangle> triangles) {
        return new ArrayList<SuperTriangle>();
    }
}
