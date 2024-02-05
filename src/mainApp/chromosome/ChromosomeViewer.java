package mainApp.chromosome;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * Class: ChromosomeViewer
 * Purpose: Viewer class to show the chromosome editor
 */
public class ChromosomeViewer {

	public ChromosomeViewer() {
	}
	public void runView(){
		/*
		 * Creating frame and title for the Frame
		 */
		JFrame myFrame = new JFrame ();
		String frameTitle = "Chromosome Viewer";
		myFrame.setTitle(frameTitle);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLocationRelativeTo(null);
		/*
		 * Creating button and panel 
		 * Creating Chromosome and Chromosome Component to draw
		 */
		JPanel buttonPanel = new JPanel();
		Chromosome chromosome = new Chromosome();

		ChromosomeComponent chromosomeComponent = new ChromosomeComponent();
		chromosomeComponent.load(chromosome);

		JButton mutateButton = new JButton("Mutate");
		JButton saveButton = new JButton("Save");
		JButton loadButton = new JButton("Load");
		JTextField box = new JTextField("1.0");
		JLabel label = new JLabel ("M Rate:_/N");

		/*
		 * Setting color for the button and background
		 */

		mutateButton.setBackground(Color.decode("#1975d1"));
		saveButton.setBackground(Color.decode("#1975d1"));
		loadButton.setBackground(Color.decode("#1975d1"));

		mutateButton.setForeground(Color.white);
		saveButton.setForeground(Color.white);
		loadButton.setForeground(Color.white);

		/*
		 * Function for three buttons
		 */
		mutateButton.addActionListener((e) -> {
			try {
			chromosome.updateMutationRate(Double.parseDouble(box.getText()));
			chromosome.mutate();
			chromosomeComponent.repaint();
			}catch(NumberFormatException a){
				System.err.println("Mutate Input Invalid");
			}
		});
		loadButton.addActionListener((e)->{
			FileDialog dialog = new FileDialog(myFrame,"chooseFile");
			dialog.setVisible(true);
			String asPath  = dialog.getFile();
			chromosome.load(asPath);
			myFrame.setTitle(asPath);
			chromosomeComponent.load(chromosome);
			myFrame.setVisible(true);
			myFrame.repaint();
	
		});

		saveButton.addActionListener((e)->{
			FileDialog dialog = new FileDialog(myFrame,"chooseFile");
			dialog.setVisible(true);
			String asPath  = dialog.getFile();
			chromosome.save(asPath);			
			chromosomeComponent.repaint();
		});
		/*
		 * Adding button to panel and panel to frame
		 */
		buttonPanel.add(loadButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(mutateButton);
		buttonPanel.add(label);

		buttonPanel.add(box);
		
		myFrame.add(buttonPanel,BorderLayout.SOUTH);
		myFrame.add(chromosomeComponent, BorderLayout.CENTER);
		
		myFrame.pack();
		myFrame.setVisible(true);
	}
}
