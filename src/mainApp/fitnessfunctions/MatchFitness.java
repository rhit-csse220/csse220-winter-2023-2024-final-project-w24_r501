package mainApp.fitnessfunctions;

import mainApp.chromosome.Chromosome;

/**
 * Class: MatchFitness
 * Purpose: Fitness function for determining how close one chomosome is to another
 */
public class MatchFitness implements FitnessFunction {
    private Chromosome target;

    public MatchFitness(Chromosome chromosome) {
        target = chromosome;
    }

    public double evaluate(Chromosome chromosome) {
        double amount = 0;
        for (int i = 0; i < chromosome.getSize(); i++) {
            if (target.getGene(i) == chromosome.getGene(i)) {
                amount += 1;
            }
        }
        return amount;
    }
}
