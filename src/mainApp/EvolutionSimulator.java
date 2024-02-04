package mainApp;

import java.util.ArrayList;

public class EvolutionSimulator {

    private SelectionMethod selectionMethod;
    private FitnessFunction fitnessFunction;
    private ArrayList<Chromosome> chromosomes;

    public EvolutionSimulator() {
        //todo
    }

    public void runGeneration() {
        //TODO
    }   

    public void getParentChromosomes () {
        //TODO
    }

    public void getPopulationChromosomes () {
        //TODO
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }
}
