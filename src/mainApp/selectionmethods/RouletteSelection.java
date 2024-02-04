package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.Chromosome;
import mainApp.SelectionMethod;
import mainApp.WeightedRandomSelection;

public class RouletteSelection implements SelectionMethod {
    @Override
    public ArrayList<Chromosome> select(HashMap<Chromosome, Double> chromosomes) {
        ArrayList<Chromosome> clist = new ArrayList<>();
        ArrayList<Double> weights = new ArrayList<>();

        for (Chromosome k : chromosomes.keySet()) {
            clist.add(k);
            weights.add(chromosomes.get(k));
        }

        return WeightedRandomSelection.getSelection(clist, weights, chromosomes.size()/2);
    }

}
