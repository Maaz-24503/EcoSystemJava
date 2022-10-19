import java.awt.*;

public class Circle extends Shape {

    /**
     * constructor
     *
     * @param location - Shape center
     * @param color    - shape Color
     * @param iSize    - shape size
     */
    public Circle(Point location, Color color, int iSize) {
        setSize(iSize);
        setLocation(location);
        setColor(color);
    }

    /**
     * to draw shape
     *
     * @param g - graphics
     */
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getCenter().x - getSize() / 2, getCenter().y - getSize() / 2, getSize(), getSize());
    }
}
