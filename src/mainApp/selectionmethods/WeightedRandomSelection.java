package mainApp.selectionmethods;


import java.util.ArrayList;

import mainApp.chromosome.Chromosome;

/**
 * Class: WeightedRandomSelection
 * Purpose: Choosing a random weighted selection for selection methods
 */
public class WeightedRandomSelection {
    public static ArrayList<Chromosome> getSelection(ArrayList<Chromosome> chromosomes, ArrayList<Double> weights,
            int selection_amount) {


        ArrayList<Chromosome> selected = new ArrayList<>();
        double total_weight = 0;
        for (Double d : weights) {
            total_weight += d;
        }

        for (int i = 0; i < selection_amount; i++) {

            int idx = 0;
            double cum_weight = 0;
            double chosen = Math.random() * total_weight;

            // select next chromosome

            while (idx != weights.size()) {
                double next_cum_weight = cum_weight + weights.get(idx);
                if (cum_weight < chosen && chosen < next_cum_weight) {
                    break;
                }
                idx += 1;
                cum_weight = next_cum_weight;
            }
            
            if (idx == weights.size()) {
                idx -= 1;
            }

            // add selected chromosome to list, remove it from the selection pool,
            // and recalculate the total weight
            total_weight -= weights.get(idx);
            Chromosome selected_chromosome = chromosomes.get(idx);
            selected.add(selected_chromosome);
            chromosomes.remove(idx);
            weights.remove(idx);

        }

        return selected;
    }
}
