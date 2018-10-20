import util.Matrix;
import util.Triangle;
import util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.time.Instant;
import java.util.Timer;

public class GTest extends Canvas implements ImageObserver {

    public static final int DEFAULT_HEIGHT = 1300;
    public static final int DEFAULT_WIDTH = 1300;

    private long time;
    Shape3D shape; //The tetrahedron I'm drawing
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    Graphics2D big ;
    Focus focus;
    Polygon polygon;
    Matrix rotate;
    boolean first = true;

    public GTest(){
        super();
        setBackground(Color.BLACK); // For that immersive outer-space look yknow
        shape = new Shape3D(new Vector(0,5,0),new Vector(-2.5,10,0),new Vector(2.5,10,0),
                            new Vector(0,10,5));

        rotate = Matrix.constructByXZRotation(Math.PI/144).leftMultiply(Matrix.constructByYZRotation(-Math.PI/288));
        rotate.setRestrictPrecision(true);
        focus = new Focus();
        time = Instant.now().toEpochMilli();
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        if(first){
            first = false;
            bi = (BufferedImage) createImage(DEFAULT_WIDTH,DEFAULT_HEIGHT); // The image to be drawn on
            big = bi.createGraphics(); // Gets the graphics object that allows me to less manually draw things
        }

        big.clearRect(0,0,DEFAULT_WIDTH,DEFAULT_HEIGHT);

        for(SuperTriangle triangle: focus.projectThrough(shape)){

            big.setColor(triangle.getColor());
            big.fillPolygon(triangle.getPolygon());

        }
        g2.drawImage(bi,0,0,this);
    }
    public void rotate(){
        shape.translate(new Vector(0,.03,0));
        shape.rotate(rotate);
        focus.turn();
    }
}
