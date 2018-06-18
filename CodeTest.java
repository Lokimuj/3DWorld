import util.Matrix;
import util.Vector;

import java.awt.*;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class CodeTest {

    public static void test(){
        double[][] thing = new double[][]{new double[]{1,-2,1,4},new double[]{1,1,0,3},new double[]{0,1,1,3},new double[]{0,0,0,1}};
        Matrix matrix = Matrix.constructByXYRotation(Math.PI/3).leftMultiply(Matrix.constructByYZRotation(Math.PI));
        System.out.println(matrix);
        Vector vector = new Vector(1,1,1);
        Instant before = Instant.now();
        for(int i = 0; i<1000000000;i++) {
            vector = matrix.leftMultiply(vector);
        }
        Instant after = Instant.now().minusMillis(before.toEpochMilli());
        System.out.println(after.toEpochMilli()+" milli seconds");
    }

    public static void test2(){
        double x = 1;
        x*=Math.pow(10,-323); //woah this shit is fucky
        System.out.println(x);
        x/=Math.pow(10,10);
        System.out.println(x);
    }

    public static void test3(){
        Matrix matrix = Matrix.constructByXZRotation(Math.PI/4);
        matrix.setRestrictPrecision(true);
        Matrix matrix1 = Matrix.constructByYZRotation(-Math.PI/2);
        matrix1.setRestrictPrecision(true);
        Vector vector = new Vector(1,1,0);
        System.out.println(matrix1.leftMultiply(vector));
    }

    public static void test4(){
        Matrix ninety = Matrix.constructByXYRotation(Math.PI/7.62);
        Matrix r1;
        Vector a = new Vector(1.000000123213,2.321424141412,3.4535324132412);
        Vector b = ninety.leftMultiply(a);
        Vector c = b.cross(a);
        Vector d = b.cross(b);
        System.out.println(a.dot(c));
        System.out.println(d);
        System.out.println(d.magnitude());
    }

    public static void guiTest(){
    }

    public static void main(String[] args) {
        test4();
    }
}
