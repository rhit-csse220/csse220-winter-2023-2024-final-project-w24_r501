package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.Chromosome;
import mainApp.SelectionMethod;
import mainApp.SortChromosomeMap;
import mainApp.WeightedRandomSelection;

public class RankSelection implements SelectionMethod {
    private static final double SELECTION_PRESSURE = 1.5;

    @Override
    public ArrayList<Chromosome> select(HashMap<Chromosome, Double> chromosomes) {
        ArrayList<Chromosome> sorted = SortChromosomeMap.sort(chromosomes);
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < chromosomes.size(); i++) {
            // https://en.wikipedia.org/wiki/Selection_(genetic_algorithm)#Rank_Selection
            weights.add((1 / chromosomes.size()) * (SELECTION_PRESSURE
                    - (2 * SELECTION_PRESSURE * ((chromosomes.size() - i - 1) / (chromosomes.size() - 1)))));
        }

        return WeightedRandomSelection.getSelection(sorted, weights, chromosomes.size()/2);
    }
}
