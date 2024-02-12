package mainApp;

import mainApp.chromosome.ChromosomeViewer;
import mainApp.evolution.EvolutionViewer;;

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

//		ChromosomeViewer cviewer = new ChromosomeViewer();
//		cviewer.runView();
		EvolutionViewer eviewer = new EvolutionViewer();
		eviewer.runViewer();
	
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