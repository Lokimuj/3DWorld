import java.util.TimerTask;

public class TimeThing extends TimerTask {

    private GTest gTest;

    public TimeThing(GTest gTest){

        this.gTest = gTest;
    }

    @Override
    public void run() {
        gTest.rotate();
//        gTest.update(gTest.getGraphics());
        gTest.update(gTest.getGraphics());
    }
}
