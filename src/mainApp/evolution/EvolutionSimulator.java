package mainApp.evolution;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mainApp.chromosome.Chromosome;
import mainApp.fitnessfunctions.FitnessFunction;
import mainApp.selectionmethods.SelectionMethod;


public class EvolutionSimulator {

    private SelectionMethod selectionMethod;
    private FitnessFunction fitnessFunction;
    private ArrayList<Chromosome> chromosomes;

    public ArrayList<Double> max;
    public ArrayList<Double> min;
    public ArrayList<Double> average;

    public GraphComponent graphcomp;

    public void setGraph(GraphComponent graphcomp) {
        this.graphcomp = graphcomp;
    }


    public void startSimulation(int pop_size, int bitlength, int seed, double mutation_rate) {
        this.chromosomes = new ArrayList<>();
        this.max = new ArrayList<>();
        this.min = new ArrayList<>();
        this.average = new ArrayList<>(); 

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
            chr.mutate();
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
        this.average.add(av);
        this.max.add(max);
        this.min.add(min); 

        graphcomp.repaint();

       ArrayList<Chromosome> survivors = selectionMethod.select(values);

       ArrayList<Chromosome> new_chr = new ArrayList<>();
       for (int i = 0; i < survivors.size(); i++) {
            new_chr.add(new Chromosome(survivors.get(i), survivors.get(survivors.size()-i-1)));
            new_chr.add(new Chromosome(survivors.get(survivors.size()-i-1), survivors.get(i)));
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
