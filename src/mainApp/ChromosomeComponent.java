package mainApp;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Class: ChromosomeComponent
 * Purpose: Display a grid representing the genes of a chromosome, and allow the user to edit the genes by clicking.
 */
public class ChromosomeComponent extends JPanel {
	/*
	 * @Param chromosomeCells : ArrayList of ChromosomeCell
	 */
	private final ArrayList<ChromosomeCell> chromosomeCells = new ArrayList<>();
	/**
	 * Class: ChromosomeCell
	 * Purpose: Represents a cell in the chromosome display grid. Will change color
	 * and update the underlying chromosome if clicked.
	 */
	private class ChromosomeCell extends JComponent {
		/*
		 * Listing instance variable
		 * @Param int index : index of the gene
		 * @Param Chromosome chromsome : Assign the calculating the chromosome
		 */
		private final int index;
		private final Chromosome chromosome;

		/**
		 * Returns true if this gene is 1 and false if it is 1.
		 * @return true if gene == 1
		 */
		public boolean getValue() {
			return (chromosome.getGene(index) == '1');
		}
		/*
		 * Constructor of other class called ChromosomeCell taking two arguments chromosome and index
		 */
		public ChromosomeCell(Chromosome chromosome, int index) {
			this.chromosome = chromosome;
			this.index = index;
			
			setPreferredSize(new Dimension(20, 20));
			/*
			 * Mouse listener to change genes if the genes are being clicked
			 */
			addMouseListener(new MouseListener() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(getValue()) {
						chromosome.setGene(index,'0' );
					} else {
						chromosome.setGene(index, '1');
					}
					repaint();
				}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
		}
		/*
		 * Drawing method
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			// Set colors
			Color fillColor;
			Color textColor;
			if (getValue()) {
				fillColor = Color.black;
				textColor = Color.white;
			} else {
				fillColor = Color.pink;
				textColor = Color.black;
			}

			// Fill rectangle and text
			g2.setColor(fillColor);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.black);

			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(0, 0, getWidth(), getHeight());
			g2.setStroke(oldStroke);

			g2.setColor(textColor);
			g2.drawString("" + index, getWidth() / 2 - 5, getHeight() / 2 + 5);
		}
	}
	/*
	 * Constructor of ChromosomeComponent
	 */
	public ChromosomeComponent(Chromosome chromosome) {
		//TODO: Set default grid size
// 		if(chromosome.getSize() == 100) {
// //			setMinimumSize(new Dimension(100, 100));
// 			this.setLayout(new GridLayout(10, 10, 1, 1));
// 		}else {
// //			setMinimumSize(new Dimension(50, 40));
// 			this.setLayout(new GridLayout(2, 10, 1, 1));
// 		}
// 		//Create cells for each gene
// 		for(int a = 0; a < chromosome.getSize(); a++) {
// 			//Create the cell
// 			ChromosomeCell chromosomecell = new ChromosomeCell(chromosome, a);
// 			//Add the cell component
// 			add(chromosomecell);
// 			//Store in the list
// 			chromosomeCells.add(chromosomecell);
// 		}
		load(chromosome);
		//Set the grid size based on the chromosome size; either 20 (4x5) or 100 (10x10)	
	}

	public void load(Chromosome chromosome) {

		removeAll();
		chromosomeCells.clear();
		
		
		if (chromosome.getSize() == 100) {
			this.setLayout(new GridLayout(10, 10, 1, 1));
		} else {
			this.setLayout(new GridLayout(2, 10, 1, 1));
		}
		
		for (int a = 0; a < chromosome.getSize(); a++) {
			System.out.println("added new cell" + a);
			ChromosomeCell chromosomeCell = new ChromosomeCell(chromosome, a);
			add(chromosomeCell);
			chromosomeCells.add(chromosomeCell);
		}
	}

}
