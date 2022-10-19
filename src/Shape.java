import java.awt.*;

public abstract class Shape {
    protected int size;
    protected Point center;
    protected Color color;

    public Shape() {

    }

    /**
     * setter for the shape size
     *
     * @param iSize - desired size
     */
    void setSize(int iSize) {
        if (iSize > 1) {
            size = iSize;
        } else {
            size = 1;
        }
    }

    /**
     * setter for shape location
     *
     * @param center - desired centre coordinate
     */
    void setLocation(Point center) {
        this.center = center;
    }

    /**
     * setter for shape color
     *
     * @param color - desired color
     */
    void setColor(Color color) {
        this.color = color;
    }

    /**
     * getter for shape size
     *
     * @return - shape size in int
     */
    int getSize() {
        return size;
    }

    /**
     * getter for shape center
     *
     * @return shape center Point
     */
    Point getCenter() {
        return center;
    }

    /**
     * getter for shape color
     *
     * @return - shape color
     */
    Color getColor() {
        return color;
    }

    /**
     * draws the shape
     *
     * @param g - graphics
     */
    public abstract void draw(Graphics g);
}
