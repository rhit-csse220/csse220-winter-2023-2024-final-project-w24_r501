package mainApp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class EvolutionSimulator {

    private SelectionMethod selectionMethod;
    private FitnessFunction fitnessFunction;
    private ArrayList<Chromosome> chromosomes;

    public double last_max;
    public double last_min;
    public double last_average;

    public EvolutionSimulator() {
        //TODO
    }

    public void startSimulation(int pop_size, int bitlength, int seed, double mutation_rate) {
        this.chromosomes = new ArrayList<>();
        Random rand = new Random(seed);
        for (int i = 0; i < pop_size; i++) {
            Chromosome chr = new Chromosome();
            chr.randomize(rand, bitlength);
            chr.updateMutationRate(mutation_rate);
            this.chromosomes.add(chr);
        }
    }


    public void runGeneration() {

        for (Chromosome chr : chromosomes) {
            chr.mutate();;
        }

        HashMap<Chromosome, Double> values = new HashMap<>();
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < chromosomes.size(); i ++) {
            Chromosome chr = this.chromosomes.get(i);
            double fitness = fitnessFunction.evaluate(chr);
            values.put(chr, fitness);
            weights.add(fitness);
        }

        double min = weights.get(0);
        double max = weights.get(0);
        double av = 0;

        for (Double w : weights) {
            min = Math.min(w, min);
            max = Math.max(w, max);
            av += w;
        }
        av /= chromosomes.size();
        this.last_average = av;
        this.last_max = max;
        this.last_min = min;

       ArrayList<Chromosome> survivors = selectionMethod.select(values);

       ArrayList<Chromosome> new_chr = new ArrayList<>();
       for (int i = 0; i < survivors.size(); i++) {
            new_chr.add(new Chromosome(survivors.get(i), survivors.get(survivors.size()-i-1)));
            new_chr.add(new Chromosome(survivors.get(survivors.size()-i-1), survivors.get(i)));
       }

       assertEquals(survivors.size(), 50);
       this.chromosomes = new_chr;

    }   


    public ArrayList<Chromosome> getPopulationChromosomes () {
        return new ArrayList<>(this.chromosomes);
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }
}
