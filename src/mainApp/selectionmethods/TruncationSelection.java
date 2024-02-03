package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.Chromosome;
import mainApp.SelectionMethod;

public class TruncationSelection implements SelectionMethod {

    @Override
    public ArrayList<Chromosome> select(HashMap<ArrayList<Chromosome>, Double> chromosomes) {
        ArrayList<Chromosome> survivors = new ArrayList<>();
        int to_remove = chromosomes.size() / 2;
        for (int i = 0; i < to_remove; i++ ) {
            survivors.add(new Chromosome(chromosomes.get(i)));
        }
        return survivors;
    }
}
