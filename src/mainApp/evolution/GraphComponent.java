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
    private int MAX_GENERATIONS = 100;

    private final ArrayList<Double> bestFitnessData = new ArrayList<>();
    private final ArrayList<Double> averageFitnessData = new ArrayList<>();
    private final ArrayList<Double> worstFitnessData = new ArrayList<>();

    private final ArrayList<Double> hammingDistanceData = new ArrayList<>();

    private final ArrayList<Double> percentUniqueData = new ArrayList<>();




    /**
     * Clears the current graph of all data.
     */
    public void clearGraph(){
        bestFitnessData.clear();
        averageFitnessData.clear();
        worstFitnessData.clear();
        hammingDistanceData.clear();
        percentUniqueData.clear();
        repaint();
    }

    public void setGenerations(int maxGenerations){
        this.MAX_GENERATIONS = maxGenerations;
    }

    // Update with new data points, assumed to be after the previous data.
    public void addEntry(double bestFitness, double averageFitness, double worstFitness,
                         double hammingDistance, double percentUnique){
        bestFitnessData.add(bestFitness);
        averageFitnessData.add(averageFitness);
        worstFitnessData.add(worstFitness);
        hammingDistanceData.add(hammingDistance);
        percentUniqueData.add(percentUnique);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(X_OFF, Y_START);

        g2.setStroke(new BasicStroke(3));

        g2.setColor(Color.BLACK);
        g2.drawLine(0, 0, 0, -100 * SCALE); //Y axis
        g2.drawLine(0, 0, 200 * SCALE, 0); //X axis

        g2.drawString("Generations", 100 * SCALE, 50);
        g2.drawString("Fitness", -100, -50 * SCALE);
        
        g2.setStroke(new BasicStroke(3));

        //Drawing the graph axes markings
        for (int i = 0; i < 11; i++) {
            //Y axis
            g2.drawLine(-5, - i * 10 * SCALE, 5, - i * 10 * SCALE);
            g2.drawString("" + i * MAX_GENERATIONS / 10, - 35, 5 - i * MAX_GENERATIONS* SCALE / 10);

            //X Axis
            g2.drawLine(SCALE * i * 20, -5, SCALE * 20 * i, 5);
            g2.drawString(""+ i * MAX_GENERATIONS/10, - 10 + SCALE * 20 * i, 30);
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

            //Hamming Distance line
            g2.setColor(Color.BLUE);
            drawPlotLine(g2,
                    i - 1, 10 * hammingDistanceData.get(i - 1),
                    i, 10 * hammingDistanceData.get(i)
            );

            //Percent Unique line
            g2.setColor(Color.ORANGE);
            drawPlotLine(g2,
                    i - 1, percentUniqueData.get(i - 1),
                    i, percentUniqueData.get(i)
            );

        }

    }


    private void drawPlotLine(Graphics2D g2, double x1, double y1, double x2, double y2){
        g2.drawLine(
                (int) x1 * SCALE * 2 / (MAX_GENERATIONS / 100), (int) (-SCALE * y1),
                (int) x2 * SCALE * 2 / (MAX_GENERATIONS / 100), (int) (-SCALE * y2)
        );
    }

}
