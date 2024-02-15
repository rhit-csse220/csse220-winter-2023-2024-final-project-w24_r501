package mainApp.fitnessfunctions;

import java.util.ArrayList;

import mainApp.chromosome.Chromosome;
import mainApp.chromosome.Chromosome.Gene;

/***
 * Class: LinearAproxFitness
 * Purpose: Generates a linear approximation to the list of points using the genetic algorithm
 */
public class LinearAproxFitness implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        int[][] points = {{1, 1}, {4, 6}, {8, 9}, {14, 70}};


        ArrayList<Gene> genes = chromosome.getGeneList();
        ArrayList<Integer> genesNew = new ArrayList<>();
        for (Gene g : genes) {
            if (g == Gene.ONE) {
                genesNew.add(1);
            } else if (g == Gene.ZERO) {
                genesNew.add(0);
            } else if (Math.random() < 0.5) {
                genesNew.add(0);
            } else {
                genesNew.add(1);
            }
        }

        double w1 = 0;
        double w2 = 0;
        for (int i = 0; i < 5; i+= 1) {
            w1 += genesNew.get(i) * Math.pow(2, i-3);
        }
        for (int i = 5; i < 10; i+= 1) {
            w2 += genesNew.get(i) * Math.pow(2, i-3-5);
        }

        double loss = 0;
        for (int[] p : points) {
            int x = p[0];
            int y = p[1];
            double result = w1 + w2 * x;
            double error = (Math.abs(y - result));
            loss += error;
        }
        loss = Math.pow(loss / points.length, 2);
        System.out.println("x:" + w1 + " y:" + w2);

        return -loss;
    }

}
