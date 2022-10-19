import java.awt.*;
import java.util.ArrayList;

public class Cannibal extends Carnivore {
    public static ArrayList<Carnivore> selfEaters = new ArrayList<>();
    private static final int maxSize = 60;
    private long t = 0;

    /**
     * random birth constructor
     */
    public Cannibal() {
        super(Color.MAGENTA);               //super constructor with color is called so that the default
        //construstor isnt called which would add this object to
        //the carnivores list
        int x1 = (int) (Math.random() * 800);
        int y1 = (int) (Math.random() * 600);
        setSize(16);
        setAlive(true);
        setCenter(new Point(x1, y1));
        setCount(400);
        setSpeed(2);
        setRep(new Circle(getCenter(), getColor(), getSize()));
        selfEaters.add(this);
    }

    /**
     * constructor with location so that offsprings can be born in the correct location
     * @param loc - location to be spawned at
     */
    public Cannibal(Point loc) {
        super(Color.MAGENTA);
        setSize(16);
        setAlive(true);
        setCenter(loc);
        setCount(400);
        setSpeed(2);
        setRep(new Circle(getCenter(), getColor(), getSize()));
        selfEaters.add(this);
    }

    /**
     * locating the prey
     * cannibals eat their own species as well
     * so in this function, we will search for the closest cannibal, carnivore or herbivore
     * which is smaller than the object itself
     */
    public void findPrey() {
        if (this.isAlive() && (!Herbivore.plantEaters.isEmpty() || !Cannibal.selfEaters.isEmpty() || !Carnivore.meatEaters.isEmpty()) && (getPrey() == null || !getPrey().isAlive() || getPrey().getSize() > this.getSize())) {
            int MinDist = Integer.MAX_VALUE;

            for (int i = 0; i < Math.max(Math.max(Herbivore.plantEaters.size(), Carnivore.meatEaters.size()), Cannibal.meatEaters.size()); i++) {
                if (i < Cannibal.selfEaters.size() && Cannibal.selfEaters.indexOf(this) != i) {
                    int dist = Math.abs(this.getCenter().x - Cannibal.selfEaters.get(i).getCenter().x)
                            + Math.abs(this.getCenter().y - Cannibal.selfEaters.get(i).getCenter().y);

                    if (dist < MinDist && this.getSize() >= Cannibal.selfEaters.get(i).getSize()) {
                        MinDist = dist;
                        this.setPrey(Cannibal.selfEaters.get(i));
                    }
                }

                if (i < Herbivore.plantEaters.size()) {
                    int dist = Math.abs(this.getCenter().x - Herbivore.plantEaters.get(i).getCenter().x)
                            + Math.abs(this.getCenter().y - Herbivore.plantEaters.get(i).getCenter().y);

                    if (dist < MinDist && this.getSize() >= Herbivore.plantEaters.get(i).getSize()) {
                        MinDist = dist;
                        this.setPrey(Herbivore.plantEaters.get(i));
                    }
                }
                if (i < Carnivore.meatEaters.size()) {
                    int dist = Math.abs(this.getCenter().x - Carnivore.meatEaters.get(i).getCenter().x)
                            + Math.abs(this.getCenter().y - Carnivore.meatEaters.get(i).getCenter().y);

                    if (dist < MinDist && this.getSize() >= Carnivore.meatEaters.get(i).getSize()) {
                        MinDist = dist;
                        this.setPrey(Carnivore.meatEaters.get(i));
                    }
                }
            }

        }
    }

    /**
     * checks the status of the current object
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

        if (getSize() > maxSize) {
            Point deathPoint = this.getCenter();
            this.setAlive(false);
            new Cannibal(new Point(deathPoint.x + 20, deathPoint.y + 20));                //2 offsprings
            // formed when
            // max size is exceeded
            new Cannibal(new Point(deathPoint.x - 20, deathPoint.y - 20));
        }
        if (!this.isAlive()) {
            Cannibal.selfEaters.remove(this);
            this.setSize(0);
            this.setSpeed(0);
            this.setPrey(null);
        }
    }
}
