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
    private final int X_OFF = 125;
    private final int Y_START = 350;
    private final int SCALE = 3;
    private final int MAX_GENERATIONS = 200;

    private final ArrayList<Double> bestFitnessData = new ArrayList<>();
    private final ArrayList<Double> averageFitnessData = new ArrayList<>();
    private final ArrayList<Double> worstFitnessData = new ArrayList<>();




    /**
     * Clears the current graph of all data.
     */
    public void clearGraph(){
        bestFitnessData.clear();
        averageFitnessData.clear();
        worstFitnessData.clear();
        repaint();
    }

    // Update with new data points, assumed to be after the previous data.
    public void addEntry(double bestFitness, double averageFitness, double worstFitness){
        bestFitnessData.add(bestFitness);
        averageFitnessData.add(averageFitness);
        worstFitnessData.add(worstFitness);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(X_OFF, Y_START);

        g2.setStroke(new BasicStroke(3));

        g2.setColor(Color.BLACK);
        g2.drawLine(0, 0, 0, -100 * SCALE);
        g2.drawLine(0, 0, 200 * SCALE, 0);

        g2.drawString("Generations", 100 * SCALE, 50);
        g2.drawString("Fitness", -100, -50 * SCALE);
        
        g2.setStroke(new BasicStroke(3));

        //Drawing the graph axes
        for (int i = 0; i < 11; i++) {
            //X axis
            g2.drawLine(-5, - i * SCALE * MAX_GENERATIONS / 10, 5, - i * SCALE * MAX_GENERATIONS / 10);
            g2.drawString("" + i * 20, - 35, 5 - i * SCALE * MAX_GENERATIONS / 10);

            //Y Axis
            g2.drawLine(SCALE * MAX_GENERATIONS * i / 10, -5, SCALE * MAX_GENERATIONS * i / 10, 5);
            g2.drawString(""+ i * MAX_GENERATIONS/10, - 10 + SCALE * MAX_GENERATIONS * i / 10, 30);
        }

        g2.setStroke(new BasicStroke(2));
        //Draw the data lines
        for (int i = 1; i < bestFitnessData.size(); i++) {
            //Best line
            g2.setColor(Color.GREEN);
            drawPlotLine(g2,
                    i - 1, bestFitnessData.get(i - 1),
                    i,  bestFitnessData.get(i)
            );

            //Average line
            g2.setColor(Color.BLACK);
            drawPlotLine(g2,
                    i - 1, averageFitnessData.get(i - 1),
                    i, averageFitnessData.get(i)
            );

            //Worst line
            g2.setColor(Color.RED);
            drawPlotLine(g2,
                    i - 1, worstFitnessData.get(i - 1),
                    i, worstFitnessData.get(i)
            );

        }

    }


    private void drawPlotLine(Graphics2D g2, double x1, double y1, double x2, double y2){
        g2.drawLine(
                (int) x1 * 2* SCALE, (int) (-SCALE * y1),
                (int) x2 * 2 * SCALE, (int) (-SCALE * y2)
        );
    }

}
