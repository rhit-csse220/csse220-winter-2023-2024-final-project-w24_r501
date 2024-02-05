package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.chromosome.Chromosome;

public class TruncationSelection implements SelectionMethod {

    @Override
    public ArrayList<Chromosome> select(HashMap<Chromosome, Double> chromosomes) {
        ArrayList<Chromosome> sorted = SortChromosomeMap.sort(chromosomes);
        ArrayList<Chromosome> survivors = new ArrayList<>();
        for (int i = sorted.size() / 2; i < sorted.size(); i++) {
            survivors.add(new Chromosome(sorted.get(i)));
        }
        return survivors;
    }
}
