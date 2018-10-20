import util.Matrix;
import util.Vector;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Shape3D implements Iterable<SuperTriangle>{

    SuperTriangle[] triangles;
    Vector center;

    public Shape3D(Vector ... vectors){
        assert vectors.length == 4;
        triangles = new SuperTriangle[4];
        triangles[0] = new SuperTriangle(vectors[0],vectors[1],vectors[2]);
        triangles[1] = new SuperTriangle(vectors[0],vectors[1],vectors[3]);
        triangles[2] = new SuperTriangle(vectors[0],vectors[2],vectors[3]);
        triangles[3] = new SuperTriangle(vectors[1],vectors[2],vectors[3]);
        center = Vector.average(triangles[0].center(),triangles[1].center(),triangles[2].center(),triangles[3].center());

        Random random = new Random();
        for(int i = 0; i<4; i++){
            SuperTriangle triangle = triangles[i];
            triangle.setColor(random.nextInt(210),random.nextInt(210),random.nextInt(210));
            Vector fromCenter = triangle.center().sub(center);
            if(triangle.getNormal().dot(fromCenter)<0){
                triangle.negateFace();
            }
        }
    }

    public void rotate(Matrix rotation){
        for(int i = 0 ; i<4; i++){
            triangles[i].rotate(rotation,center);
        }
    }

    @Override
    public Iterator<SuperTriangle> iterator() {
        return Arrays.asList(triangles).iterator();
    }

    public void translate(Vector d){
        for(int i = 0; i<4;i++){
            triangles[i].translate(d);
        }
        center.addToThis(d);
    }
}
