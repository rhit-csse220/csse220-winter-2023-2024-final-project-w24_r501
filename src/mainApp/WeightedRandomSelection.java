package mainApp;

import java.util.ArrayList;

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
            while (true) {
                double next_cum_weight = cum_weight + weights.get(idx);
                if (cum_weight < chosen && chosen < next_cum_weight) {
                    break;
                }
                idx += 1;
                cum_weight = next_cum_weight;
            }

            // add selected chromosome to list, remove it from the selection pool,
            // and recalculate the total weight
            Chromosome selected_chromosome = chromosomes.get(idx);
            selected.add(selected_chromosome);
            chromosomes.remove(selected_chromosome);
            total_weight -= weights.get(idx);

        }

        return selected;
    }
}
