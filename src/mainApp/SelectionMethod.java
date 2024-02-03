package mainApp;

import java.util.ArrayList;
import java.util.HashMap;

public interface SelectionMethod {
    public ArrayList<Chromosome> select(HashMap<ArrayList<Chromosome>, Double> chromosomes);
    
}
