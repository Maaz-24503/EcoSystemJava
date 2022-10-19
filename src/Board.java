import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = -40;
    private final int DELAY = 25;

    private int t = 0;

    private Timer timer;
    private int x, y;

    public Board() {
        initBoard();
    }


    private void initBoard() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        x = INITIAL_X;
        y = INITIAL_Y;

        //For loops for construction of livingthings

        for (int i = 0; i < 300; i++) {
            new Plants();
        }

        for (int i = 0; i < 50; i++) {
            new Herbivore();
        }

        for (int i = 0; i < 4; i++) {
            new Carnivore();
        }

        for (int i = 0; i < 16; i++) {
            new Cannibal();
        }

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Draw(g);
    }


    //this function runs every 25 ms so we print each Arraylist to refresh the screen everytime

    private void Draw(Graphics g) {
        //the plants loop runs such that teh growth of the vegetation is as great as the number of plants
        //already present as this is realistic due to seeds so if plants finish there will be no regrowth

        for (int i = 0; i < Plants.vegetation.size(); i++) {
            Plants.vegetation.get(i).getRep().draw(g);
        }
        for (int i = 0; i < Herbivore.plantEaters.size(); i++) {
            Herbivore.plantEaters.get(i).getRep().draw(g);
        }
        for (int i = 0; i < Carnivore.meatEaters.size(); i++) {
            Carnivore.meatEaters.get(i).getRep().draw(g);
        }
        for (int i = 0; i < Cannibal.selfEaters.size(); i++) {
            Cannibal.selfEaters.get(i).getRep().draw(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    //we check for any changes and update the arraylists every 25 ms using this function
    public void actionPerformed(ActionEvent e) {

        x += 1;
        y += 1;

        t++;

        for (int i = 0; i < Plants.vegetation.size(); i++) {
            Plants.vegetation.get(i).checkList();
        }

        for (int i = 0; i < Herbivore.plantEaters.size(); i++) {
            Herbivore.plantEaters.get(i).move();
            Herbivore.plantEaters.get(i).eatPlant();
            Herbivore.plantEaters.get(i).checkList();
        }

        for (int i = 0; i < Carnivore.meatEaters.size(); i++) {
            Carnivore.meatEaters.get(i).move();
            Carnivore.meatEaters.get(i).eatPrey();
            Carnivore.meatEaters.get(i).checkList();
        }

        for (int i = 0; i < Cannibal.selfEaters.size(); i++) {
            Cannibal.selfEaters.get(i).move();
            Cannibal.selfEaters.get(i).eatPrey();
            Cannibal.selfEaters.get(i).checkList();
        }

        if (y > B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }

        repaint();
    }
}
