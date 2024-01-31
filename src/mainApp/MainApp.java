package mainApp;

import javax.swing.JFrame;

/**
 * Class: MainApp
 * @author W24-R501
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	//test commit 
	//test
	//comment
	//test!!!

	
	private void runApp() {
		
		ChromosomeViewer viewer =  new ChromosomeViewer();
		viewer.runView();
	
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();		
	} // main

}