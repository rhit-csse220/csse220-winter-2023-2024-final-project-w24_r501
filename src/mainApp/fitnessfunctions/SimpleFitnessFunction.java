package mainApp.fitnessfunctions;

import mainApp.chromosome.Chromosome;

/**
 * Class: SimpleFitnessFunction
 * Purpose: Implementing fitness function based on amount of 1 genes
 */
public class SimpleFitnessFunction implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        int amount = 0;
        for (int i = 0; i < chromosome.getGeneList().size(); i++) {
            if (chromosome.getGene(i) == '1') {
                amount += 1;
            }
        }

        return amount;
    }

}