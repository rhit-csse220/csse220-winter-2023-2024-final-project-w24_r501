package mainApp.evolution;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import mainApp.chromosome.Chromosome;
import mainApp.fitnessfunctions.MatchFitness;
import mainApp.fitnessfunctions.MaxConsecutiveFitness;
import mainApp.fitnessfunctions.SimpleFitnessFunction;
import mainApp.selectionmethods.RankSelection;
import mainApp.selectionmethods.RouletteSelection;
import mainApp.selectionmethods.TruncationSelection;

/**
 * Class: EvolutionViewer
 * Purpose: Handling the frame for displaying evolution progress
 */
public class EvolutionViewer {

    private enum SimulationState {
        STOPPED,
        RUNNING,
        PAUSED
    }

    public EvolutionSimulator sim = new EvolutionSimulator();

    private SimulationState simState = SimulationState.STOPPED;
    
    public void runViewer() {
    	/*
    	 * JFrame for graphics and to view Evolution
    	 */
        JFrame progressFrame = new JFrame("Evolution Viewer");
        progressFrame.setPreferredSize(new Dimension(1200, 500));
        progressFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame chromosomeFrame = new JFrame("Chromsome Viewer");
        chromosomeFrame.setPreferredSize(new Dimension(1100, 500));
        chromosomeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chromosomeFrame.setLocation(300, 300);

        JPanel buttons = new JPanel();
        GraphComponent graph = new GraphComponent();

        //Target chromosome
        Chromosome targetChromosome = new Chromosome();
        targetChromosome.load("smileyFace.txt");

        LiveChromosomeComponent liveChromosomeComponent = new LiveChromosomeComponent(targetChromosome);

        //Evolution Configuration Options

        JTextField mutateBox = new JTextField("0.5");
        buttons.add(new JLabel("M Rate:_/N"));
        buttons.add(mutateBox);

        String[] selectionOp = {"Truncation", "Rank", "Roulette"};
        JComboBox<String> selectionBox = new JComboBox<>(selectionOp);
        buttons.add(new JLabel("Selection"));
        buttons.add(selectionBox);


        String[] fitnessOp= {"Match", "Max", "Simple"};
        JComboBox<String> fitnessBox = new JComboBox<>(fitnessOp);
        buttons.add(new JLabel("Fitness"));
        buttons.add(fitnessBox);


        JTextField generationsBox = new JTextField("100");
        buttons.add(new JLabel("Generations"));
        buttons.add(generationsBox);

        JCheckBox crossOverCheckBox = new JCheckBox();
        buttons.add(new JLabel ("CrossOver"));
        buttons.add(crossOverCheckBox);

        JTextField populationBox = new JTextField("100");
        buttons.add(new JLabel("Population"));
        buttons.add(populationBox);

        JTextField genomeBox = new JTextField("100");
        buttons.add(new JLabel("Genome Length"));
        buttons.add(genomeBox);

        JTextField elitismBox = new JTextField("5");
        buttons.add(new JLabel ("Elitism"));
        buttons.add(elitismBox);

        JTextField terminateCondition = new JTextField("100");
        buttons.add(new JLabel("Terminate Condition"));
        buttons.add(terminateCondition);
        
        JButton startButton = new JButton("Start Simulation");
        buttons.add(startButton);

        Timer t = new Timer(50, (e) -> {

            if(simState == SimulationState.RUNNING){
                //Run the next generation
                if(sim.runGeneration()){
                    //Update the graph
                    graph.addEntry(sim.getMaxFitness(), sim.getAverageFitness(), sim.getMinFitness(),
                            sim.getHammingDistance(), sim.getPercentUnique());

                    //Update the chromosome live viewer
                    liveChromosomeComponent.setNewGeneration(sim.getCurrentGeneration());
                    System.out.println("Max: "+sim.getMaxFitness()+ " Average: "+sim.getAverageFitness()+
                            " Min: "+sim.getMinFitness()+" Hamming: "+sim.getHammingDistance());
                } else {
                    simState = SimulationState.STOPPED;
//                    chromosomeFrame.setVisible(false);
                    startButton.setText("Start");
                }
                graph.repaint();
                liveChromosomeComponent.repaint();
            }
        });
        t.start();

        startButton.addActionListener((e) -> {
            //If this is the start of the 
        	
        	
        	
        	
            if(simState == SimulationState.STOPPED){
                graph.clearGraph();

                //Check the fitness function and selection method
                if (selectionBox.getSelectedIndex() == 0) {
                    sim.setSelectionMethod(new TruncationSelection());
                } else if (selectionBox.getSelectedIndex() == 1) {
                    sim.setSelectionMethod(new RouletteSelection());
                } else {
                    sim.setSelectionMethod(new RankSelection());
                }

                if (fitnessBox.getSelectedIndex() == 0) {
                    sim.setFitnessFunction(new MatchFitness(targetChromosome));
                } else if(fitnessBox.getSelectedIndex() == 1){
                    sim.setFitnessFunction(new MaxConsecutiveFitness());
                } else if(fitnessBox.getSelectedIndex() == 2){
                    sim.setFitnessFunction(new SimpleFitnessFunction());
                }



                int generations = Integer.parseInt(generationsBox.getText());
                int genomes = Integer.parseInt(genomeBox.getText());
                double mutate_rate = Double.parseDouble(mutateBox.getText());
                int population = Integer.parseInt(populationBox.getText());
                double elitism_rate = Double.parseDouble(elitismBox.getText()) / 100.0;
                boolean crossover = crossOverCheckBox.isSelected();
                int terminateAmount = (int) Double.parseDouble(terminateCondition.getText());

                sim.startSimulation(population, genomes, (int)(100 * Math.random()), mutate_rate, elitism_rate,
                        generations, terminateAmount, crossover, targetChromosome);
                graph.setGenerations(generations);
                chromosomeFrame.setVisible(true);

                simState = SimulationState.RUNNING;
                startButton.setText("Pause");

            } else if(simState == SimulationState.RUNNING){
                startButton.setText("Continue");
                simState = SimulationState.PAUSED;
            } else if(simState == SimulationState.PAUSED){
                startButton.setText("Pause");
                simState = SimulationState.RUNNING;
            }
            
        });

        chromosomeFrame.add(liveChromosomeComponent);
        chromosomeFrame.pack();
        chromosomeFrame.setVisible(true);
        progressFrame.add(graph);
        progressFrame.add(buttons, BorderLayout.SOUTH);
        progressFrame.pack();
        progressFrame.setVisible(true);

    }
}
