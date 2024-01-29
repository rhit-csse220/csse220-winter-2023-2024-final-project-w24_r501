package mainApp;

import java.util.ArrayList;

/**
 * Class: Chromosome
 * Purpose: Stores a list of genes and other chromosome-specific information like mutation rate. Can be told to randomly
 * mutate, and can load/save from or to a file.
 */
public class Chromosome {
	private ArrayList<Character> geneList = new ArrayList<>();;
	private int size;
	private double mutationRate;
	
	private static final int DEFAULT_SIZE = 100;
	
	
	public Chromosome() {
		this.size = DEFAULT_SIZE;

		// Initialize the gene list with 0s
		for (int a = 0; a < this.size; a++) {
			this.geneList.add('0');
		}

		// Provide a default mutation rate.
		this.mutationRate = 1.0/ this.size;
		
	}
	public void save(String filePath) {
		
	}

	public void load(String filePath) {
		
	}

	/**
	 * Runs a mutation chance for each gene with this chromosome's mutation rate.
	 * If the check passes, then the bit in that gene is flipped.
	 *
	 * By default, the mutation rate is set to 1 / N where N is the size of the chromosome (e.g, 100).
	 */
	public void mutate() {
		
		for(int a = 0; a < geneList.size(); a++) {

			//Run a check to see if this cell should mutate
			if(Math.random() < this.mutationRate) {
				if(this.geneList.get(a) == '0') {
					this.geneList.set(a, '1');
				}else {
					this.geneList.set(a, '0');
				}
				
			}
		}
	}
	public char getGene(int index) {
		return this.geneList.get(index);
	}
	public void setGene(int index, char letter) {
		this.geneList.set(index, letter);
	}
	public ArrayList<Character> getGeneList(){
		return new ArrayList<>(this.geneList);
	}

	public int getSize(){return size;}

	public void setMutationRate(double mutationRate){
		this.mutationRate = mutationRate;
	}
}
