package mainApp.fitnessfunctions;

import mainApp.chromosome.Chromosome;
import mainApp.chromosome.Chromosome.Gene;

/**
 * Class: SimpleFitnessFunction
 * Purpose: Implementing fitness function based on amount of 1 genes
 */
public class SimpleFitnessFunction implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        int amount = 0;
        for (int i = 0; i < chromosome.getSize(); i++) {
            if (chromosome.getGene(i) == Gene.ONE) {
                amount += 1;
            } else if (chromosome.getGene(i) == Gene.QUESTION) {
                // treat question genes as random in eval stage 
                if (Math.random() < 0.5) {
                    amount += 1;
                }
            }
        }
        return amount;
    }

}