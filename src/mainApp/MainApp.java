package mainApp;

import javax.swing.JFrame;

import mainApp.evolution.EvolutionSimulator;
import mainApp.fitnessfunctions.SimpleFitnessFunction;
import mainApp.selectionmethods.TruncationSelection;
import mainApp.chromosome.ChromosomeViewer;;

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

		ChromosomeViewer viewer = new ChromosomeViewer();
		viewer.runView();

		// EvolutionViewer simulation = new EvolutionViewer();

		EvolutionSimulator sim = new EvolutionSimulator();
		sim.startSimulation(100, 100, (int) Math.random() * 100, 1);
		sim.setFitnessFunction(new SimpleFitnessFunction());
		sim.setSelectionMethod(new TruncationSelection());

		for (int i = 0; i < 200; i++) {
			if (i % 5 == 0) {
				sim.runGeneration();
				System.out.println("Min: " + sim.last_min + ", Average: " + sim.last_average + ", Max: " + sim.last_max);
			}
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