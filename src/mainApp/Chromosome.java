package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: Chromosome
 * Purpose: Stores a list of genes and other chromosome-specific information like mutation rate. Can be told to randomly
 * mutate, and can load/save from or to a file.
 * @Param geneList : list of gene
 * @Param size : the size of the gene
 * @Param mutationRate : rate of mutation of the the gene
 */
public class Chromosome {
	/*
	 * Instance variable
	 */
	private ArrayList<Character> geneList = new ArrayList<>();;
	private int size;
	private double mutationRate;
	
	private static final int DEFAULT_SIZE = 100;
	
	/*
	 * Constructor
	 */
	public Chromosome() {
		this.size = DEFAULT_SIZE;

		// Initialize the gene list with 0s
		for (int a = 0; a < this.size; a++) {
			this.geneList.add('0');
		}
		// Provide a default mutation rate.
		this.mutationRate = 1.0/ this.size;
		
	}
	/*
	 * Save and load method for the button
	 */
	public void save(String filePath) {
		String contains = "";
		for ( int a = 0; a<geneList.size();a++) {
			contains  += geneList.get(a) + "\n";
		}
		try {
			File file = new File(filePath);
			file.createNewFile();
			FileWriter filewriter;
			filewriter = new FileWriter(file);
			filewriter.write(contains,0,contains.length());
			filewriter.close();
			
		} catch(NullPointerException e) {
			System.err.println("No file selected");
		
		}catch (IOException e) {
			System.err.println("Fail to write to file");
		}
		
	}

	public void load(String filePath) {
		try {
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			String contains ="";
			while(scanner.hasNext()) {
				contains += scanner.nextLine();
			}
			if(!(contains.length()==100 || contains.length()==20)) {
				throw new InvalidChromosomeFormatException();
			}
			for(int a =0;a<contains.length();a++) {
				if(!(contains.charAt(a)=='1' || contains.charAt(a)=='0')) {
					throw new InvalidChromosomeFormatException();
				}
				geneList.set(a, contains.charAt(a));
			}
			scanner.close();
			
		}catch(NullPointerException e) {
			System.err.println("No file selected");
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Fail to open File");
			return;
		}
		catch(InvalidChromosomeFormatException e){
			System.err.println("File format is invalid");
		}	
		catch(IOException e) {
			System.err.println("File Io Exception");
		}
	
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
	/*
	 * Getter and Setter method to get gene, set gene and get the geneList and the size. As well as setting mutationRate.
	 */
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

	public void upDateMutationRate(double mutationRate){
		this.mutationRate = mutationRate/this.size;
	}
}
