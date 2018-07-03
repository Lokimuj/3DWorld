package repr.shapes;

import util.Matrix;
import util.Triangle;
import util.Vector;

import java.awt.*;

public class SuperTriangle extends Triangle {
    private Vector normal;
    private Color color;

    public SuperTriangle(Vector... vectors ){
        super(vectors);
        normal = vectors[0].sub(vectors[1]).cross(vectors[2].sub(vectors[1])).unit();
        color = Color.WHITE;
    }

    @Override
    public void rotate(Matrix rotation) {
        super.rotate(rotation);
        normal = rotation.leftMultiply(normal);
    }

    @Override
    public void rotate(Matrix rotation, Vector origin) {
        super.rotate(rotation, origin);
        normal = rotation.leftMultiply(normal);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(int r, int g, int b){
        this.color = new Color(r,g,b);
    }

    public void setColor(Color color){
        this.color = color;
    }
    public void negateFace(){
        normal.scalarMultiplyThis(-1);
    }

    public Vector getNormal() {
        return normal;
    }
}
