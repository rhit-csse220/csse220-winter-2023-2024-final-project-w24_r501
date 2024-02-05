package mainApp.fitnessfunctions;

import mainApp.chromosome.Chromosome;

public interface FitnessFunction {
    public double evaluate(Chromosome chromosome);
}
