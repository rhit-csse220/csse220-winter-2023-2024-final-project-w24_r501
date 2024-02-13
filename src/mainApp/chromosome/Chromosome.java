package mainApp.chromosome;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * Class: Chromosome
 * Purpose: Stores a list of genes and other chromosome-specific information
 * like mutation rate. Can be told to randomly
 * mutate, and can load/save from or to a file.
 *
 * @Param geneList : list of gene
 * @Param size : the size of the gene
 * @Param mutationRate : rate of mutation of the the gene
 */
public class Chromosome {

    public enum Gene {
        ONE, ZERO, QUESTION
    }
    /*
     * Instance variable
     */
    private ArrayList<Gene> geneList = new ArrayList<>();;
    public double mutationRate;
    private static final int DEFAULT_SIZE = 100;

    /*
     * Constructor
     */
    public Chromosome() {

        // Initialize the gene list with 0s
        for (int a = 0; a < DEFAULT_SIZE; a++) {
            this.geneList.add(Gene.ZERO);
        }
        // Provide a default mutation rate.
        this.mutationRate = 1.0 / DEFAULT_SIZE;

    }

    public Chromosome(Chromosome chromosome) {
        this();
        this.geneList.clear();
        for (int i = 0; i < chromosome.getSize(); i++) {
            this.geneList.add(chromosome.getGene(i));
        }

        this.mutationRate = chromosome.mutationRate;
    }

    public Chromosome(Chromosome c1, Chromosome c2) {
        for (int i = 0; i < c1.getSize() / 2; i++) {
            this.geneList.add(c1.getGene(i));
        }

        for (int i = c1.getSize() / 2; i < c1.getSize(); i++) {
            this.geneList.add(c2.getGene(i));
        }

        this.mutationRate = c1.mutationRate;

    }

    /*
     * Save and load method for the button
     */
    public void save(String filePath) {
        String contains = "";
        for (Gene character : geneList) {
            if (character == Gene.ZERO) {
                contains += "0" + "\n";
            } else if (character == Gene.ONE) {
                contains += "1" + "\n";
            } else if (character == Gene.QUESTION) {
                contains += "?" + "\n";
            }
        }
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileWriter filewriter;
            filewriter = new FileWriter(file);
            filewriter.write(contains, 0, contains.length());
            filewriter.close();

        } catch (NullPointerException e) {
            System.err.println("No file selected");

        } catch (IOException e) {
            System.err.println("Fail to write to file");
        }

    }

    public void load(String filePath) {
        ArrayList<Gene> backup = new ArrayList<>(geneList);
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            String contains = "";
            geneList.clear();
            while (scanner.hasNext()) {
                contains += scanner.nextLine();
            }
            if (!(contains.length() == 100 || contains.length() == 20)) {
                throw new InvalidChromosomeFormatException();
            }
            for (int a = 0; a < contains.length(); a++) {
                if (!(contains.charAt(a) == '1' || contains.charAt(a) == '0')) {
                    throw new InvalidChromosomeFormatException();
                }
                
                if (contains.charAt(a) == '1') {
                    geneList.add(Gene.ONE);
                } else if (contains.charAt(a) == '0') {
                    geneList.add(Gene.ZERO);
                } else {
                    geneList.add(Gene.QUESTION);
                }
            }
            scanner.close();

        } catch (NullPointerException e) {
            System.err.println("No file selected");
            geneList = backup;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Fail to open File");
            geneList = backup;
            return;
        } catch (InvalidChromosomeFormatException e) {
            System.err.println("File format is invalid");
            geneList = backup;
        }

    }

    /**
     * Runs a mutation chance for each gene with this chromosome's mutation rate.
     * If the check passes, then the bit in that gene is flipped.
     * <p>
     * By default, the mutation rate is set to 1 / N where N is the size of the
     * chromosome (e.g, 100).
     */
    public void mutate() {
        for (int a = 0; a < geneList.size(); a++) {
            // TODO add question
            // Run a check to see if this cell should mutate
            if (Math.random() < this.mutationRate) {
                if (this.geneList.get(a) == Gene.ZERO) {
                    this.geneList.set(a, Gene.ONE);
                } else {
                    this.geneList.set(a, Gene.ZERO);
                }
            }
        }
    }

    /*
     * Getter and Setter method to get gene, set gene and get the geneList and the
     * size. As well as setting mutationRate.
     */
    public Gene getGene(int index) {
        if (index >= geneList.size())
            return Gene.ZERO;
        return this.geneList.get(index);
    }

    public void setGene(int index, Gene g) {
        this.geneList.set(index, g);
    }

    public ArrayList<Gene> getGeneList() {
        return new ArrayList<>(this.geneList);
    }

    public void setGeneList(ArrayList<Gene> genes) {
        this.geneList = genes;
    }

//    public int getSize() {
//        return geneList.size();
//    }

    public void updateMutationRate(double mutationRate) {
        this.mutationRate = mutationRate / geneList.size();
    }
    /*
     * Randomize Method to create 100 or 20 Chromosome
     */
    public void randomize(Random rand, int bitsize) {
        if (!(bitsize == 100 || bitsize == 20)) {
            throw new UnsupportedOperationException("Genome size can only be 20 or 100");
        }

        this.geneList = new ArrayList<>();
        for (int i = 0; i < bitsize; i++) {
            double r = rand.nextDouble();
            if (r < 0.5) {
                geneList.add(i, Gene.ZERO);
            } else {
                geneList.add(i, Gene.ONE);
            }
        }
    }

    public int getSize() {
        return geneList.size();
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)) return true;
        Chromosome other = (Chromosome) obj;
        return other.getGeneList().equals(getGeneList());
    }
}
