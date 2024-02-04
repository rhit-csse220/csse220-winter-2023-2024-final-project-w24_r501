package mainApp.fitnessfunctions;

import mainApp.Chromosome;
import mainApp.FitnessFunction;

public class SimpleFitnessFunction implements FitnessFunction {

    @Override
    public double evaluate(Chromosome chromosome) {
        int amount = 0;
        for (int i = 0; i < chromosome.getSize(); i++) {
            if (chromosome.getGene(i) == '1') {
                amount += 1;
            }
        }

        return amount;
    }

}