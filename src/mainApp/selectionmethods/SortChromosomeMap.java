package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mainApp.chromosome.Chromosome;

/** Class: SortChromosomeMap
 * Purpose: Implementing sort algorithm that keeps secondary list in sync 
 */
public class SortChromosomeMap {
    public static ArrayList<Chromosome> sort(HashMap<Chromosome, Double> chromosomes) {
        ArrayList<ChromosomeDatastructure> datastructures = new ArrayList<>();
        for (Chromosome k : chromosomes.keySet()) {
            datastructures.add(new ChromosomeDatastructure(k, chromosomes.get(k)));
        }

        if(chromosomes.isEmpty()) return new ArrayList<>();

        List<ChromosomeDatastructure> sorted = mergeSort(datastructures);
        ArrayList<Chromosome> to_return = new ArrayList<>();
        for (ChromosomeDatastructure c : sorted) {
            to_return.add(c.chromosome);
        }
        return to_return;

    }

    static class ChromosomeDatastructure {
        public double value;
        public Chromosome chromosome;

        ChromosomeDatastructure(Chromosome chromosome, double value) {
            this.chromosome = chromosome;
            this.value = value;
        }
    }

    private static List<ChromosomeDatastructure> mergeSort(List<ChromosomeDatastructure> chromosomes) {
        if (chromosomes.size() == 1) {
            return chromosomes;
        }
        if (chromosomes.size() == 2) {
            List<ChromosomeDatastructure> sorted = new ArrayList<>();
            if (chromosomes.get(0).value < chromosomes.get(1).value) {
                sorted.add(chromosomes.get(0));
                sorted.add(chromosomes.get(1));
            } else {
                sorted.add(chromosomes.get(1));
                sorted.add(chromosomes.get(0));
            }
        }
        int half = chromosomes.size() / 2;
        List<ChromosomeDatastructure> sorted1 = new ArrayList<>(mergeSort(chromosomes.subList(0, half)));
        List<ChromosomeDatastructure> sorted2 = new ArrayList<>(
                mergeSort(chromosomes.subList(half, chromosomes.size())));
        List<ChromosomeDatastructure> new_list = new ArrayList<>();

        while (!sorted1.isEmpty() && !sorted2.isEmpty()) {
            double sorted1value = sorted1.get(0).value;
            double sorted2value = sorted2.get(0).value;

            if (sorted1value <= sorted2value) {
                new_list.add(sorted1.get(0));
                sorted1.remove(0);
            } else {
                new_list.add(sorted2.get(0));
                sorted2.remove(0);
            }
        }

        while (!sorted1.isEmpty()) {
            new_list.add(sorted1.get(0));
            sorted1.remove(0);
        }

        while (!sorted2.isEmpty()) {
            new_list.add(sorted2.get(0));
            sorted2.remove(0);
        }

        return new_list;
    }
}
