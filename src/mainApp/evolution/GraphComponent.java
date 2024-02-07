package mainApp.evolution;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class: GraphComponent
 * Purpose: Displaying progress of an EvolutionSimulator
 */
public class GraphComponent extends JComponent {
    private EvolutionSimulator sim;
    public JFrame frame;
    private final int X_OFF = 125;
    private final int Y_START = 350;
    private final int SCALE = 3;
    private final int MAX_GENERATIONS = 200;

    public GraphComponent(EvolutionSimulator sim) {
        this.sim = sim;
        sim.setGraph(this);
    }

    public void setSim(EvolutionSimulator sim) {
        this.sim = sim;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(X_OFF, Y_START);
        ArrayList<Double> max = sim.max;
        ArrayList<Double> min = sim.min;
        ArrayList<Double> average = sim.average;

        g2.setStroke(new BasicStroke(3));

        g2.setColor(Color.BLACK);
        g2.drawLine(0, 0, 0, 0- 100 * SCALE);
        g2.drawLine(0, 0, 0+ 200 * SCALE, 0);

        g2.drawString("Generations", 100 * SCALE, 50);
        g2.drawString("Fitness", -100, -50 * SCALE);
        
        g2.setStroke(new BasicStroke(3));

        for (int i = 0; i < 11; i++) {
            g2.drawLine(-5, - i * SCALE * MAX_GENERATIONS / 10, 5, - i * SCALE * MAX_GENERATIONS / 10);
            g2.drawString(""+i*20, - 35, 5 - i * SCALE * MAX_GENERATIONS / 10);

            g2.drawLine(SCALE * MAX_GENERATIONS * i / 10, -5, SCALE * MAX_GENERATIONS * i / 10, 5);
            g2.drawString(""+i*MAX_GENERATIONS/10, - 10 + SCALE * MAX_GENERATIONS * i / 10, 30);
        }
        
        if (sim.max != null) {
            ArrayList<Integer> max_int = new ArrayList<>();
            ArrayList<Integer> average_int = new ArrayList<>();
            ArrayList<Integer> min_int = new ArrayList<>();

            // annoying casting 
            for (int i = 0; i < max.size(); i++) {
                max_int.add((int) Math.round(max.get(i)));
                average_int.add((int) Math.round(average.get(i)));
                min_int.add((int) Math.round(min.get(i)));
            }
            
            for (int i = 0; i < max.size() - 1; i++) {
                g2.setColor(Color.RED);
                g2.drawLine(i*SCALE, -SCALE * min_int.get(i), (i+1)*SCALE, -SCALE * min_int.get(i+1));
                g2.setColor(Color.BLACK);
                g2.drawLine(i*SCALE, -SCALE * average_int.get(i), (i+1)*SCALE, -SCALE * average_int.get(i+1));
                g2.setColor(Color.GREEN);
                g2.drawLine(i*SCALE, -SCALE * max_int.get(i), (i+1)*SCALE,  -SCALE * max_int.get(i+1));
            }
        }

    }

}
