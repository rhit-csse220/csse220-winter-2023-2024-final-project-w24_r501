package mainApp.evolution;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mainApp.chromosome.Chromosome;
import mainApp.chromosome.Chromosome.Gene;
import mainApp.fitnessfunctions.FitnessFunction;
import mainApp.selectionmethods.SelectionMethod;
import mainApp.selectionmethods.SortChromosomeMap;

/**
 * Class: EvolutionSimulator
 * Purpose: Simulating genetic algorithm
 */
public class EvolutionSimulator {

    private SelectionMethod selectionMethod;
    private FitnessFunction fitnessFunction;
    private ArrayList<Chromosome> chromosomes= new ArrayList<>();


    private double max = 0;
    private double min = 0;
    private double average = 0;
    private double hammingDistance = 0;

    private double percentUnique = 0;
    private int generationCount = 0;
    private int maxGenerations = 0;
    private double elitismRate = 0;
    private int populationSize = 0;
    private int terminateLevel = 0;

    private Chromosome target;

    private boolean hasTerminated = false;
    private boolean doCrossover = false;



    public void startSimulation(int pop_size, int chromosomeLength, int seed, double mutation_rate, double elitismRate,
                                int maxGenerations, int terminateLevel, boolean doCrossover, Chromosome target) {
        this.populationSize = pop_size;
        this.generationCount = 0;
        this.maxGenerations = (maxGenerations % 2 == 1) ? maxGenerations - 1 : maxGenerations;
        this.chromosomes = new ArrayList<>();
        this.elitismRate = elitismRate;
        this.terminateLevel = terminateLevel;
        if(elitismRate > 100) this.elitismRate = 100;
        this.doCrossover = doCrossover;
        this.target = target;

        hasTerminated = false;
            //Generate random chromosomes
        Random rand = new Random(seed);
        for (int i = 0; i < pop_size; i++) {
            Chromosome chr = new Chromosome();
            chr.randomize(rand, chromosomeLength);
            chr.updateMutationRate(mutation_rate);
            this.chromosomes.add(chr);
        }
       
    }
   

    public boolean runGeneration() {
        //Check if we have reached the end
        if(generationCount >= maxGenerations || hasTerminated){
            return false;
        }
        System.out.println(chromosomes.size());

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
        double hammingSum = 0;
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
            hammingSum += hammingDistance(chr, target);
        }
        average = sum / chromosomes.size();
        max = currentMax;
        min = currentMin;
        hammingDistance = hammingSum / chromosomes.size();

        if(max == terminateLevel){
            hasTerminated = true;
        }

        //check for duplicates
        int sumUnique = 0;
        for(int i = 0; i < chromosomes.size(); i++){
            for(int j = 0; j < chromosomes.size(); j++){
                if(i == j) continue;
                if(!values.get(chromosomes.get(i)).equals(values.get(chromosomes.get(j)))){
                    if(!chromosomes.get(i).equals(chromosomes.get(j))) sumUnique++;
                }
                
            }
        }
        percentUnique = (double) sumUnique / chromosomes.size();


        //Find how many elites we are taking out
        int numberOfElites = (int) (elitismRate * populationSize);
        if(numberOfElites % 2 == 1) numberOfElites++; //Snap to the nearest even number


        ArrayList<Chromosome> sortedByFitness = SortChromosomeMap.sort(values);
        ArrayList<Chromosome> elites = new ArrayList<>();

        //Take out elites and store them temporarily
        for(int i = 1; i <= numberOfElites; i++){
            elites.add(new Chromosome(sortedByFitness.get(sortedByFitness.size() - i)));
        }

        //From this point on, the non-elite chromosome survivor list has an EVEN size

        ArrayList<Chromosome> survivors = selectionMethod.select(values, (populationSize/2) - numberOfElites/2);

        //Now, it should have HALF that size (exactly half because its even)


        /*
        Crossover approach:
        - Add elites back into the survivors TEMPORARILY.
        - For each elite, pair it with the next highest regular chromosome below the elites
        - Perform crossover on a random point and ignore one of the offspring from it ig??
        - Replace the non-elite parent with the singly chosen offspring????

        - Once elites are exhausted, start pairing the remaining normal chromosomes together
        - For each resulting offspring replace both parents (because crossover results in 2 children)
        - Take back out all the elites from the top of the list

        - Mutate the remaining normal chromosomes, but instead of making two children per parent,
        make one mutated child per parent chromosome.
        - Finally, re-add the elites for the last time into the final survivor list
         */


        ArrayList<Chromosome> new_chr = new ArrayList<>();

        // Re-populate for the next generation
        if(doCrossover){

            //Loop and crossover from the rear of the survivor list until all elites are used.
            //For elites, only the non-elite parent is replaced with the crossover child

            //Loop through the remainder of the survivor list, this time replacing both parents
            for(int i = 0; i < survivors.size() -1; i++){
                Chromosome[] children = onePointCrossover(survivors.get(i), survivors.get(i + 1));
                survivors.set(i, children[0]);
                survivors.set(i + 1, children[1]);
            }


        }
        for (Chromosome survivor : survivors) {
            //Create children mutated from their parents
            Chromosome first_child = new Chromosome(survivor);
            first_child.mutate();
            Chromosome second_child = new Chromosome(survivor);
            second_child.mutate();

            //Add the new children
            new_chr.add(first_child);
            new_chr.add(second_child);

        }

//        System.out.println("before elite size: "+new_chr.size());
        new_chr.addAll(elites);
//        System.out.println("after elite size: "+new_chr.size());

        this.chromosomes = new_chr;





        generationCount++;
        return true;
    }


    /**
     * Performs a single point crossover with two parents, and returns two offspring.
     * @param one first chromosome
     * @param two second chromosome
     * @return array with two offspring chromosomes, the first contains the chromosome with the largest amount of
     * the first chromosome's genes if it's not evenly split
     */
    private Chromosome[] onePointCrossover(Chromosome one, Chromosome two){
        //Find a crossover point 0 <= x <= chromosomeSize
        int crossoverPoint = (int) ((one.getSize() + 1) * Math.random());

        Chromosome oneSideChild = new Chromosome(one);
        Chromosome twoSideChild = new Chromosome(two);

        //Loop through to the crossover point
        for(int i = 0; i < crossoverPoint; i++){
            //Store genes at this location
            Gene originalOneGene = oneSideChild.getGene(i);
            Gene originalTwoGene = twoSideChild.getGene(i);

            //Swap the genes
            oneSideChild.setGene(i, originalTwoGene);
            twoSideChild.setGene(i, originalOneGene);
        }

        Chromosome[] offspring = new Chromosome[2];

        //Order so that the first offspring has the most of the first chromosome's genes in it
        if(crossoverPoint > one.getSize() / 2){
            offspring[0] = oneSideChild;
            offspring[1] = twoSideChild;
        } else {
            offspring[0] = twoSideChild;
            offspring[1] = oneSideChild;
        }

        return offspring;

    }
    private double hammingDistance(Chromosome one, Chromosome two){
        ArrayList<Gene> oneGenes = one.getGeneList();
        ArrayList<Gene> twoGenes = two.getGeneList();

        int distance = 0;
        for(int i = 0; i < oneGenes.size(); i++){
            if(oneGenes.get(i) != twoGenes.get(i)) distance++;
        }

        return (double) distance / one.getSize();
    }

    /**
     * Return the latest generation's chromosomes (should be sorted)
     * @return chromosome list
     */
    public ArrayList<Chromosome> getCurrentGeneration(){
        return new ArrayList<>(chromosomes);
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
     * Returns the latest generation's percent of unique individuals.
     * @return percent of unique individuals
     */
    public double getPercentUnique(){
        return percentUnique;
    }
    /**
     * Return the latest generation's minimum fitness
     * @return hamming distance
     */
    public double getHammingDistance(){
        return hammingDistance;
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
