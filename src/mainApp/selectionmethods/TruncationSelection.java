package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.Chromosome;
import mainApp.SelectionMethod;
import mainApp.SortChromosomeMap;

public class TruncationSelection implements SelectionMethod {

    @Override
    public ArrayList<Chromosome> select(HashMap<Chromosome, Double> chromosomes) {
        ArrayList<Chromosome> sorted = SortChromosomeMap.sort(chromosomes);
        ArrayList<Chromosome> survivors = new ArrayList<>();
        int to_remove = chromosomes.size() / 2;
        for (int i = 0; i < to_remove; i++ ) {
            survivors.add(new Chromosome(sorted.get(i)));
        }
        return survivors;
    }
}
