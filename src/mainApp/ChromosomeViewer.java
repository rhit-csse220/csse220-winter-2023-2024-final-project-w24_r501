package mainApp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mainApp.ChromosomeComponent.ChromosomeCell;

public class ChromosomeViewer {
	public ChromosomeViewer() {
		JFrame myFrame = new JFrame ();
		String frameTitle = "Chromosome Viewer";
		myFrame.setTitle(frameTitle);
	    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JPanel panel = new JPanel();
	    Chromosome chromosome = new Chromosome();

	    ChromosomeComponent component = new ChromosomeComponent(chromosome);
	    
	    
	    JButton mutateButton = new JButton("MutateButton");
	    JButton saveButton = new JButton("saveButton");
	    JButton loadButton = new JButton("loadButton");
	    
	    mutateButton.addActionListener((e) -> {
	    	chromosome.mutate();
	    	component.repaint();
	    });
	    
	    panel.add(loadButton);
	    panel.add(saveButton);
	    panel.add(mutateButton);
	    
	    myFrame.add(panel,BorderLayout.SOUTH);
	    myFrame.add(component, BorderLayout.CENTER);

		myFrame.pack();
	    myFrame.setVisible(true);
	}
}
