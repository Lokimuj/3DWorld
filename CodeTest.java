import util.Matrix;
import util.Vector;

import java.applet.Applet;
import java.awt.*;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class CodeTest{

    public static void test(){
        double[][] thing = new double[][]{new double[]{1,-2,1,4},new double[]{1,1,0,3},new double[]{0,1,1,3},new double[]{0,0,0,1}};
        Matrix matrix = Matrix.constructByXYRotation(Math.PI/3);
        matrix.setRestrictPrecision(true);
        System.out.println(matrix);
        Vector vector = new Vector(1,1,1);
        Instant before = Instant.now();
        for(int i = 0; i<6;i++) {
            vector = matrix.leftMultiply(vector);
        }
        System.out.println(vector);
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
        System.setProperty("sun.awt.noerasebackground", "true");
        Polygon tri = new Polygon(new int[]{1,20,30},new int[]{3,2,50},3);
        Canvas canvas = new Canvas();

        canvas.setBounds(0,0,GTest.DEFAULT_WIDTH,GTest.DEFAULT_HEIGHT);
        canvas.setVisible(true);
        Frame frame = new Frame();
        GTest gTest = new GTest();
        frame.add(gTest);
        frame.setSize(GTest.DEFAULT_WIDTH,GTest.DEFAULT_HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
        long start = Instant.now().toEpochMilli();
        while(true){
            gTest.rotate();
            gTest.repaint();
            long now = Instant.now().toEpochMilli();
            if(now-start<16){
                try {
                    Thread.sleep(16-(now-start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            start = now;
        }
//        Timer timer = new Timer();
//        TimeThing timeThing = new TimeThing(gTest);
//        timer.scheduleAtFixedRate(timeThing,0,16);
    }

    public static void main(String[] args) {
        guiTest();
    }
}
