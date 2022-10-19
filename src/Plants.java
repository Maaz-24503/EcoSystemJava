import java.awt.*;
import java.util.ArrayList;

public class Plants extends LivingThings {
    public static ArrayList<Plants> vegetation = new ArrayList<>();
    private long t = 1;       //timer to control growth

    /**
     * Plant production
     * we only need a randomized constructor for the growth of plants since they wont be leaving behind offsprings
     */
    public Plants() {
        int x1 = (int) (Math.random() * 800);
        int y1 = (int) (Math.random() * 600);
        setCenter(new Point(x1, y1));
        setAlive(true);
        setColor(Color.GREEN);
        setCount(400);
        setSize(6);
        setSpeed(0);
        setRep(new Circle(getCenter(), getColor(), getSize()));
        vegetation.add(this);
    }

    /**
     * checks plants growth, reproduction and death status
     */
    public void checkList() {
        t++;
        if (t % 75 == 0)              //vegetation growth
            new Plants();
        if (t % getCount() == 0)      //individual plant growth
            this.setSize(getSize() + 1);
        if (!isAlive())
            Plants.vegetation.remove(this);
    }
}
