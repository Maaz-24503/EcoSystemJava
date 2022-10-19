import java.awt.*;
import java.util.ArrayList;

public class Herbivore extends LivingThings {
    public static ArrayList<Herbivore> plantEaters = new ArrayList<>();
    private static final int maxSize = 50;
    private long t = 0;         //timer to control death

    /**
     * constructor for random birth
     */
    public Herbivore() {
        int x1 = (int) (Math.random() * 800);
        int y1 = (int) (Math.random() * 600);
        setSize(10);
        setAlive(true);
        setCenter(new Point(x1, y1));
        setColor(Color.CYAN);
        setCount(400);
        setSpeed(1);
        findPrey();
        setRep(new Circle(getCenter(), getColor(), getSize()));
        plantEaters.add(this);
    }

    /**
     * constructor with location so we can produce offsprings where the herbivore dies
     * @param p - location to be spawned at
     */
    public Herbivore(Point p) {
        setSize(10);
        setAlive(true);
        setCenter(p);
        setColor(Color.CYAN);
        setCount(400);
        setSpeed(1);
        findPrey();
        setRep(new Circle(getCenter(), getColor(), getSize()));
        plantEaters.add(this);
    }



    /**
     * this function works such that it locates the nearest plant that is smaller that animal
     * it does not check direct distance, instead we have added the x and y distance as this is the total distance
     * of the path the animal will cover as it takes one step in only x or y direction at a time
     */
    public void findPrey() {
        if (this.isAlive() && !Plants.vegetation.isEmpty() && (getPrey() == null || !getPrey().isAlive())) {
            int MinDist = Integer.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < Plants.vegetation.size(); i++) {
                int dist = Math.abs(this.getCenter().x - Plants.vegetation.get(i).getCenter().x)
                        + Math.abs(this.getCenter().y - Plants.vegetation.get(i).getCenter().y);

                if (dist < MinDist && this.getSize() > Plants.vegetation.get(i).getSize()) {
                    MinDist = dist;
                    index = i;
                }
            }
            this.setPrey(Plants.vegetation.get(index));
        }
        if (getPrey() != null)
            if (getPrey().getSize() > getSize())
                setPrey(null);
    }

    /**
     * checks if the herbivore has collided with the plant and if it can eat it and also
     * grows the herbivore and kills the plant
     */
    public void eatPlant() {
        if (this.hasCollided(getPrey()) && getPrey().getSize() < this.getSize()) {
            this.setSize(this.getSize() + getPrey().getSize() / 3);
            t = 0;
            getPrey().setAlive(false);
            setPrey(null);
            findPrey();
        }
    }

    /**
     * checks the list for the animals status
     */
    public void checkList() {
        t++;
        if (getPrey() != null)
            if (!getPrey().isAlive())
                setPrey(null);

        if (t == getCount())
            setAlive(false);

        if (isAlive())
            findPrey();
        //check to die and create offsprings if size is exceeded
        if (getSize() > maxSize) {
            Point deathPoint = this.getCenter();
            this.setAlive(false);
            new Herbivore(new Point(deathPoint.x, deathPoint.y + 20));
            new Herbivore(new Point(deathPoint.x + 20, deathPoint.y + 20));
            new Herbivore(new Point(deathPoint.x + 20, deathPoint.y));
            new Herbivore(new Point(deathPoint.x - 20, deathPoint.y));
            new Herbivore(new Point(deathPoint.x - 20, deathPoint.y - 20));
            new Herbivore(new Point(deathPoint.x, deathPoint.y - 20));
            new Herbivore(new Point(deathPoint.x - 20, deathPoint.y + 20));
            new Herbivore(new Point(deathPoint.x + 20, deathPoint.y - 20));
        }
        if (!this.isAlive()) {
            Herbivore.plantEaters.remove(this);
            this.setSize(0);
            this.setSpeed(0);
            this.setPrey(null);
        }
    }
}
