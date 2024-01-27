package mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class ChromosomeComponent extends JComponent {
	private ArrayList<ChromosomeCell> chromosomeCells;

	class ChromosomeCell extends JComponent{
		private int index;
		private Chromosome chromosome;
		public boolean getValue() {
			return (chromosome.getGene(index)=='1');
		}
		public ChromosomeCell(Chromosome chromosome, int index) {
			this.chromosome =chromosome;
			this.index= index;
			
			this.setPreferredSize(new Dimension(20, 20));
			this.setMaximumSize(new Dimension(30, 30));
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(getValue()) {
						chromosome.setGene(index,'0' );
					}else {
						chromosome.setGene(index, '1');
					}
					repaint();
				}
			});
		}
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			Color colorfill;
			Color colorText;
			if(getValue()) {
				colorfill=Color.black;
				colorText=Color.white;
			}else {
				colorfill=Color.pink;
				colorText= Color.black;
			}
			g2.setColor(colorfill);
			g2.fillRect(0, 0,this.getWidth(), this.getHeight());
			g2.setColor(Color.black);
			g2.drawRect(0, 0, this.getWidth(), this.getHeight());
			g2.setColor(colorText);
			g2.drawString(""+index, getWidth() / 2 - 5, getHeight() / 2);
		}
	}
	
	public ChromosomeComponent(Chromosome chromosome) {
		this.chromosomeCells =new ArrayList<>();
		//TODO: Set default grid size 
		
		
		ArrayList<Character> chromosomeList = chromosome.getListOfCharacter();
		for(int a =0; a< chromosomeList.size();a++) {
			ChromosomeCell chromosomecell;
				chromosomecell =  new ChromosomeCell(chromosome, a);
			this.add(chromosomecell);
			this.chromosomeCells.add(chromosomecell);
			
		}
		if(chromosomeList.size()==100) {
			this.setLayout(new GridLayout(10, 10));
		}else {
			this.setLayout(new GridLayout(5,4, 1, 1));
		}
		
	}
//	public ArrayList<Character> getList(){
//		ArrayList<Character> new_list = new ArrayList<>();
//		for(ChromosomeCell chromosomecell : this.chromosomecells) {
//			if( chromosomecell.getchoosen()) {
//				new_list.add('1');
//			}else {
//				new_list.add('0');
//			}
//		}
//		return new_list;
//	}
	
}
