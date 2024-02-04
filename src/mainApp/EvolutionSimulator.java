package mainApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class EvolutionSimulator {

    private SelectionMethod selectionMethod;
    private FitnessFunction fitnessFunction;
    private ArrayList<Chromosome> chromosomes;

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
        for (int i = 0; i < chromosomes.size(); i ++) {
            Chromosome chr = this.chromosomes.get(i);
            values.put(chr, fitnessFunction.evaluate(chr));
        }

       ArrayList<Chromosome> survivors = selectionMethod.select(values);

       ArrayList<Chromosome> new_chr = new ArrayList<>();
       for (int i = 0; i < survivors.size(); i++) {
            new_chr.add(new Chromosome(survivors.get(i), survivors.get(survivors.size()-i-1)));
       }
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
