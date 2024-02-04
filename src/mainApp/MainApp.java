package mainApp;

import javax.swing.JFrame;

import mainApp.fitnessfunctions.MaxConsecutiveFitness;
import mainApp.fitnessfunctions.SimpleFitnessFunction;
import mainApp.selectionmethods.RankSelection;
import mainApp.selectionmethods.RouletteSelection;
import mainApp.selectionmethods.TruncationSelection;

/**
 * Class: MainApp
 * 
 * @author W24-R501
 *         <br>
 *         Purpose: Top level class for CSSE220 Project containing main method
 *         <br>
 *         Restrictions: None
 */
public class MainApp {

	private void runApp() {

		// ChromosomeViewer viewer = new ChromosomeViewer();
		EvolutionViewer simulation = new EvolutionViewer();

		EvolutionSimulator sim = new EvolutionSimulator();
		sim.startSimulation(100, 100, 1, 1/100);
		sim.setFitnessFunction(new SimpleFitnessFunction());
		sim.setSelectionMethod(new RouletteSelection());

		for (int i = 0; i < 100; i++) {
			sim.runGeneration();
			System.out.println("Min: " + sim.last_min + ", Average: " + sim.last_average + ", Max: " + sim.last_max);
		}

	} // runApp

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	} // main

}