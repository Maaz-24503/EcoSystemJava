import java.awt.*;
import java.util.ArrayList;

public class Carnivore extends LivingThings {
    public static ArrayList<Carnivore> meatEaters = new ArrayList<>();
    private static final int maxSize = 28;
    private long t = 0;        //death counter

    /**
     * random birth constructor
     */
    public Carnivore() {
        int x1 = (int) (Math.random() * 800);
        int y1 = (int) (Math.random() * 600);
        setSize(16);
        setAlive(true);
        setCenter(new Point(x1, y1));
        setColor(Color.RED);
        setCount(400);
        setSpeed(2);
        setRep(new Circle(getCenter(), getColor(), getSize()));
        meatEaters.add(this);
    }

    /**
     * constructor so that the child class does not get added in this classes static arraylist
     * @param c - color
     */
    public Carnivore(Color c) {
        setColor(c);
    }

    /**
     * constructor with location so that offsprings can be born in the correct location
     * @param loc - location to be spawned at
     */
    public Carnivore(Point loc) {
        setSize(16);
        setAlive(true);
        setCenter(loc);
        setColor(Color.RED);
        setCount(400);
        setSpeed(2);
        setRep(new Circle(getCenter(), getColor(), getSize()));
        meatEaters.add(this);
    }

    /**
     * checks if the carnivore has collided with the prey and if it can eat it and also
     * grows the carnivore and kills the prey
     */
    public void eatPrey() {
        if (this.hasCollided(getPrey()) && getPrey().getSize() <= this.getSize()) {
            this.setSize(this.getSize() + getPrey().getSize() / 3);
            t = 0;
            getPrey().setAlive(false);
            setPrey(null);
            findPrey();
        }
    }

    /**
     * check for the status of the animal
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
        //check for the exceeding of max size and production of offsprings
        if (getSize() > maxSize) {
            Point deathPoint = this.getCenter();
            this.setAlive(false);
            new Carnivore(new Point(deathPoint.x, deathPoint.y + 20));
            new Carnivore(new Point(deathPoint.x + 20, deathPoint.y));
            new Carnivore(new Point(deathPoint.x - 20, deathPoint.y));
            new Carnivore(new Point(deathPoint.x, deathPoint.y - 20));
        }
        if (!this.isAlive()) {
            Carnivore.meatEaters.remove(this);
            this.setSize(0);
            this.setSpeed(0);
            this.setPrey(null);
        }
    }

    /**
     * locating of prey
     * carnivores will not hunt there own species
     * so we will only be hunting for cannibals and herbivores in this search
     * which is smaller than the object itself
     */
    public void findPrey() {
        if (this.isAlive() && (!Herbivore.plantEaters.isEmpty() || !Cannibal.selfEaters.isEmpty() || !Carnivore.meatEaters.isEmpty()) && (getPrey() == null || !getPrey().isAlive() || getPrey().getSize() > this.getSize())) {
            int MinDist = Integer.MAX_VALUE;

            for (int i = 0; i < Math.max(Math.max(Herbivore.plantEaters.size(), Carnivore.meatEaters.size()), Cannibal.meatEaters.size()); i++) {
                if (i < Cannibal.selfEaters.size()) {
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
            }

        }
    }


}
