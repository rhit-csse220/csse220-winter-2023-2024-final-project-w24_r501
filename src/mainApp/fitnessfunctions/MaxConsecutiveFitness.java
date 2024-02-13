package mainApp.fitnessfunctions;

import java.util.ArrayList;

import mainApp.chromosome.Chromosome;
import mainApp.chromosome.Chromosome.Gene;

/**
 * Class: MaxConsecutiveFitness
 * Purpose: Fitness function for determining longest sequence of 1s in a 
 * chromosome
 */
public class MaxConsecutiveFitness implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        ArrayList<Gene> genes = chromosome.getGeneList();
        int idx = 0;
        int max_conc = 0;
        int cur_conc = 0;

        while (idx != genes.size()) {
            if (genes.get(idx) == Gene.ONE) {
                cur_conc += 1;
            } else {
                cur_conc = 0;
            }

            if (max_conc < cur_conc) {
                max_conc = cur_conc;
            }
            idx += 1;
        }

        return max_conc;

    }

}
