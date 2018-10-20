import util.Matrix;
import util.Triangle;
import util.Vector;

import java.awt.*;

public class SuperTriangle extends Triangle {
    private Vector normal; //Unit vector that points where this triangle is facing
    private Color color;
    private boolean isVisible; //Uhh not sure what this was for
    public Polygon polygon;
    int r = 225;
    int g = 0;
    int b = 0;

    public SuperTriangle(Vector... vectors ){
        super(vectors);
        normal = vectors[0].sub(vectors[1]).cross(vectors[2].sub(vectors[1])).unit();
        updateColor();
        polygon = new Polygon(this.toXs(),toYs(),3);
    }

    @Override
    public void rotate(Matrix rotation) {
        super.rotate(rotation);
        normal = rotation.leftMultiply(normal);
        polygon.xpoints = toXs();
        polygon.ypoints = toYs();
        double dot = normal.dot(new Vector(0,0,1));
        isVisible = dot>0;
        updateColor();
    }

    @Override
    public void rotate(Matrix rotation, Vector origin) {
        super.rotate(rotation, origin);
        normal = rotation.leftMultiply(normal);
        polygon.xpoints = toXs();
        polygon.ypoints = toYs();
        double dot = normal.dot(new Vector(0,0,1));
        isVisible = dot>0;
        updateColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
        updateColor();
    }

    public void setColor(Color color){
        this.color = color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Polygon getPolygon() {
        return polygon;
    }
    public void negateFace(){
        normal.scalarMultiplyThis(-1);
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * Does 'lighting' by darkening the color more the more the shape is facing down
     */
    private void updateColor(){
        double dot = normal.dot(new Vector(0,0,1));

        color = new Color((int)(r/2+dot*r/2),(int)(g/2+dot*g/2),(int)(b/2+dot*b/2));

    }
}
