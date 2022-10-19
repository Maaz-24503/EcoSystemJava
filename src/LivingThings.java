import java.awt.*;

public abstract class LivingThings {

    private Circle rep;
    private Color color;
    private int size;
    private boolean isAlive;
    private Point center;
    private int count;
    private int speed;
    private LivingThings prey;

    /**
     *
     * @return circle representing the object
     */
    public Circle getRep() {
        return rep;
    }

    /**
     *
     * @return true or false based on whether it is alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     *
     * @return the color of the object
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return Counter to keep track of growth and death
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return size of object
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return int speed of the object
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @return origin of the shape
     */
    public Point getCenter() {
        return center;
    }

    /**
     *
     * @param alive - whether it is alive or not
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * whenever center is updated we change the circles representation as well
     * @param center - origin of shape
     */
    public void setCenter(Point center) {
        this.center = center;
        this.setRep(new Circle(this.center, this.color, this.size));
    }

    /**
     *  whenever the color is changed the color of the circles also automatically changes
     */
    public void setColor(Color color) {
        this.color = color;
        this.setRep(new Circle(this.center, this.color, this.size));
    }

    /**
     *
     * @param count - reset status counter
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @param rep - circle
     */
    public void setRep(Circle rep) {
        this.rep = rep;
    }

    /**
     * whenever the size is changed the circles size also automatically changes accordingly
     * @param size - diameter of the object
     */
    public void setSize(int size) {
        this.size = size;
        this.setRep(new Circle(center, color, size));
    }

    /**
     *
     * @param speed - int speed of the object
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     *
     * @param prey - set target
     */
    public void setPrey(LivingThings prey) {
        this.prey = prey;
    }

    /**
     *
     * @return objects target
     */
    public LivingThings getPrey() {
        return prey;
    }

    /**
     * //in this function we check if the living thing has collided usually with its prey
     * //but we havent made it precise using pythagoras theorem because square rooting iteration everytime would
     * //be very expensive and it would slow the code down
     * //so the circles have been assumed rectangles instead because at such a small scale the inaccuracy goes
     * //almost unnoticed
     * @param X - the prey
     * @return true or false whether it has collided or not
     */
    public boolean hasCollided(LivingThings X) {
        if (X != null) {
            int Dx = Math.abs(this.getCenter().x - X.getCenter().x);
            int Dy = Math.abs(this.getCenter().y - X.getCenter().y);
            if (Dx <= (this.size / 2 + X.getSize() / 2) && Dy <= (this.size / 2 + X.getSize() / 2)) {
                return true;
            } else
                return false;
        } else return false;
    }

    /**
     *  the move function works such that he living thing first locates the coordinate difference with the prey
     *  and only then does it start moving with one step at a time in either the x or y direction
     */
    public void move() {
        if (this.isAlive() && prey != null) {
            int Dx = this.center.x - prey.getCenter().x;
            int Dy = (this.center.y - prey.getCenter().y);
            if (Math.abs(Dx) > Math.abs(Dy)) {
                if (Dx > 0) {
                    this.setCenter(new Point(this.center.x - this.speed, this.center.y));
                } else {
                    this.setCenter(new Point(this.center.x + this.speed, this.center.y));
                }
            } else {
                if (Dy > 0) {
                    this.setCenter(new Point(this.center.x, this.center.y - this.speed));
                } else {
                    this.setCenter(new Point(this.center.x, this.center.y + this.speed));
                }
            }
        }
    }

    /**
     *  the checkList function in every class will be used to update the status of the object such as
     *  its death status
     */
    public abstract void checkList();
}

