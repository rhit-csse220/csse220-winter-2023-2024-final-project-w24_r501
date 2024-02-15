package mainApp.evolution;

import mainApp.chromosome.Chromosome;
import mainApp.chromosome.Chromosome.Gene;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Class: LiveChromosomeCompoent
 * Provides a live view of the chromosomes in the simulation
 */
public class LiveChromosomeComponent extends JPanel {

    class SingleChromosome extends JComponent {

        private int geneSize;
        private Chromosome chromosome;

        SingleChromosome(Chromosome chromosome, int geneSize){
            this.chromosome = chromosome;
            this.geneSize = geneSize;

        }

        public void setNewChromosome(Chromosome chromosome){
            this.chromosome = chromosome;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            //Find the box size
            int rows, cols;
            if(chromosome.getSize() == 100){
                rows = 10;
            } else{
                rows = 2;
            }
            cols = 10;

//            g2.drawRect(0,0, 40, 40);

            //Draw each gene
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    Gene gen = chromosome.getGene((i * cols) + j);
                    if (gen == Gene.ONE) {
                        g2.setColor(Color.GREEN);
                    } else if (gen == Gene.ZERO) {
                        g2.setColor(Color.BLACK);
                    } else {
                        g2.setColor(Color.YELLOW);
                    }

                    g2.fillRect(geneSize * j, geneSize * i, geneSize, geneSize);
                }
            }

        }
    }

    class ChromosomeGrid extends JPanel {

        /**
         * Draws a single small chromosome
         */

        private ArrayList<SingleChromosome> cells = new ArrayList<>();

        ChromosomeGrid(ArrayList<Chromosome> initialChromosomes){

            //Create initial grid
            for(int i = 0; i < initialChromosomes.size(); i++){
                cells.add(new SingleChromosome(initialChromosomes.get(i), 5));
                add(cells.get(i));
            }

            int height = (int) Math.sqrt(initialChromosomes.size());
            setLayout(new GridLayout(height, height + 1, 2, 2));

            setPreferredSize(new Dimension(450, 450));

        }

        /**
         * Re-links all the cells with a new list of chromosomes.
         * @param chromosomes new list of chromosomes.
         */
        public void setNewChromosomes(ArrayList<Chromosome> chromosomes){
            for(int i = 0; i < chromosomes.size(); i++){
                if(i == cells.size()) cells.add(new SingleChromosome(chromosomes.get(i), 3));
                else cells.get(i).chromosome = chromosomes.get(i);
            }


            repaint();
        }

    }

    private ChromosomeGrid chromosomeGrid;
    private SingleChromosome targetChromosome;

    private SingleChromosome leaderChromosome;

    /**
     * Creates a new live chromosome viewer with a given target chromsome.
     * @param target target chromosome
     */
    LiveChromosomeComponent(Chromosome target){

        //Fill grid with blank chromosomes initially
        ArrayList<Chromosome> blankChromosomes = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            blankChromosomes.add(new Chromosome());
        }


        chromosomeGrid = new ChromosomeGrid(blankChromosomes);
        add(chromosomeGrid, BorderLayout.WEST);


        JPanel larger = new JPanel();
        //Set the target chromosome
        targetChromosome = new SingleChromosome(target, 10);
        targetChromosome.setPreferredSize(new Dimension(100, 100));
        larger.add(targetChromosome, 0);


        //Set the leader
        leaderChromosome = new SingleChromosome(blankChromosomes.get(0), 10);
        leaderChromosome.setPreferredSize(new Dimension(100, 100));
        larger.add(leaderChromosome, 1);

        add(larger, BorderLayout.WEST);



    }


    public void setNewGeneration(ArrayList<Chromosome> generation){
        chromosomeGrid.setNewChromosomes(generation);

        leaderChromosome.setNewChromosome(generation.get(generation.size() - 1));
//        leaderChromosome.setPreferredSize(new Dimension(100, 100));
        repaint();
    }
}
