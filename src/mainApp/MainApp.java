package mainApp;

import javax.swing.JFrame;

import mainApp.evolution.EvolutionSimulator;
import mainApp.evolution.EvolutionViewer;
import mainApp.fitnessfunctions.SimpleFitnessFunction;
import mainApp.selectionmethods.RankSelection;
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

		ChromosomeViewer cviewer = new ChromosomeViewer();
		cviewer.runView();
		EvolutionViewer eviewer = new EvolutionViewer();
		eviewer.runViewer();

		

		// EvolutionViewer simulation = new EvolutionViewer();

		// EvolutionSimulator sim = new EvolutionSimulator();
		// sim.startSimulation(100, 100, (int) Math.random() * 100, 1);
		// sim.setFitnessFunction(new SimpleFitnessFunction());
		// sim.setSelectionMethod(new TruncationSelection());
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