package mainApp;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	private ArrayList<Character> genes;
	private int size;
	private double mutation_chance;
	
	public static final int DEFAULT_SIZE= 20;
	
	
	public Chromosome() {
		this.size =DEFAULT_SIZE;
		this.genes = new ArrayList<Character>();
		for ( int a=0; a<this.size;a++) {
			this.genes.add('0');
		}
		this.mutation_chance = 1.0/this.size;
		
	}
	public void save() {
		
	}
	public void load() {
		
	}
	public void mutate() {
		
		for( int a =0; a<genes.size();a++) {
			
			if(Math.random()<this.mutation_chance) {
				System.out.println("did smth");
				if(this.genes.get(a)=='0') {
					this.genes.set(a, '1');
				}else {
					this.genes.set(a, '0');
				}
				
			}
		}
	}
	public char getGene(int index) {
		return this.genes.get(index);
	}
	public void setGene(int index, char letter) {
		this.genes.set(index, letter);
	}
	public ArrayList<Character> getListOfCharacter(){
		return new ArrayList<Character>(this.genes);
	}
	public void toggleGene() {
		
	}
	public void setMutationRate(double mutationRate){
		this.mutation_chance= mutationRate;
	}
}
