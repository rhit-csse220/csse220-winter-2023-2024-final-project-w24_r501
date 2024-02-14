package mainApp.selectionmethods;

import java.util.ArrayList;
import java.util.HashMap;

import mainApp.chromosome.Chromosome;

public interface SelectionMethod {
    public ArrayList<Chromosome> select(HashMap<Chromosome, Double> chromosomes, int amount);
}
