package mainApp.evolution;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mainApp.chromosome.Chromosome;
import mainApp.fitnessfunctions.FitnessFunction;
import mainApp.selectionmethods.SelectionMethod;

/**
 * Class: EvolutionSimulator
 * Purpose: Simulating genetic algorithm
 */
public class EvolutionSimulator {

    private SelectionMethod selectionMethod;
    private FitnessFunction fitnessFunction;
    private ArrayList<Chromosome> chromosomes= new ArrayList<>();


    public double max = 0;
    public double min = 0;
    public double average = 0;
    int generationCount = 0;
    int maxGenerations = 0;



    public void startSimulation(int pop_size, int chromosomeLength, int seed, double mutation_rate, int maxGenerations) {
        this.generationCount = 0;
        this.maxGenerations = maxGenerations;
        this.chromosomes = new ArrayList<>();

        //Generate random chromosomes
        Random rand = new Random(seed);
        for (int i = 0; i < pop_size; i++) {
            Chromosome chr = new Chromosome();
            chr.randomize( chromosomeLength);
            chr.updateMutationRate(mutation_rate);
            this.chromosomes.add(chr);
        }
       
    }
    public ArrayList<Chromosome> getChromosomeList() {
    	if(this.chromosomes.isEmpty()) {
    		 for (int i = 0; i < 100; i++) {
    	            Chromosome chr = new Chromosome();
    	            chr.randomize( 100);
    	            chr.updateMutationRate(10);
    	            this.chromosomes.add(chr);
    	        }
    	}
    	return this.chromosomes;
    }
   

    public boolean runGeneration() {
        //Check if we have reached the end
        if(generationCount >= maxGenerations){
            return false;
        }

//        //Mutate all the chromosomes
//        for (Chromosome chr : chromosomes) {
//            chr.mutate();
//        }


        //Create a map to store the chromosomes and their fitness's
        HashMap<Chromosome, Double> values = new HashMap<>();

        //Evaluate each chromosome and find analytics
        double sum = 0;
        double currentMax = 0;
        double currentMin = 0;
        for (Chromosome chr : chromosomes) {
            double fitness = fitnessFunction.evaluate(chr);
            values.put(chr, fitness);

            if(currentMax == 0){
                currentMax = fitness;
                currentMin = fitness;
            }
            currentMax = Math.max(fitness, currentMax);
            currentMin = Math.min(fitness, currentMin);
            sum += fitness;
        }
        average = sum / chromosomes.size();
        max = currentMax;
        min = currentMin;



        ArrayList<Chromosome> survivors = selectionMethod.select(values);

        // Re-populate
        ArrayList<Chromosome> new_chr = new ArrayList<>();
        for (Chromosome survivor : survivors) {
            //Create children mutated from their parents
            Chromosome first_child = new Chromosome(survivor);
            first_child.mutate();
            Chromosome second_child = new Chromosome(survivor);
            second_child.mutate();

            //Add the new children
            new_chr.add(first_child);
            new_chr.add(second_child);

            System.out.println("rate: "+survivor.mutationRate);
        }

        this.chromosomes = new_chr;
        generationCount++;
        return true;
    }


    /**
     * Return the latest generation's maximum fitness
     * @return maximum fitness
     */
    public double getMaxFitness(){
        return max;
    }

    /**
     * Return the latest generation's minimum fitness
     * @return minimum fitness
     */
    public double getMinFitness(){
        return min;
    }

    /**
     * Return the latest generation's average fitness
     * @return average fitness
     */
    public double getAverageFitness(){
        return average;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }
}
