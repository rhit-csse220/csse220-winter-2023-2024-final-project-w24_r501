package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.chromosome.Chromosome;

/**
 * Class: RouletteSelection
 * Purpose: Implementing roulette selection algorithm 
 */
public class RouletteSelection implements SelectionMethod {
    @Override
    public ArrayList<Chromosome> select(HashMap<Chromosome, Double> chromosomes, int amount) {
        ArrayList<Chromosome> clist = new ArrayList<>();
        ArrayList<Double> weights = new ArrayList<>();

        for (Chromosome k : chromosomes.keySet()) {
            clist.add(k);
            weights.add(chromosomes.get(k));
        }

        return WeightedRandomSelection.getSelection(clist, weights, amount);
    }

}
