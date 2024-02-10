package mainApp.evolution;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import mainApp.chromosome.Chromosome;
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
    private  ArrayList<Chromosome> list = new ArrayList<Chromosome>();
    public EvolutionViewer() {
    }
    
    public void runViewer() {
    	/*
    	 * JFrame for graphics and to view Evolution
    	 */
        JFrame frame = new JFrame("Evolution Viewer");
        frame.setPreferredSize(new Dimension(1100, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JFrame myFrame = new JFrame("Population");
        myFrame.setPreferredSize(new Dimension(600,600));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel buttons = new JPanel();
        GraphComponent graph = new GraphComponent();
        
		
        list = sim.getChromosomeList();
        
        GridLayout layout = new GridLayout(10,10,0,0);
        myFrame.setLayout(layout);
        for(int a=0;a<list.size();a++) {
        	myFrame.add(list.get(a));
        	
        }
       

        //Evolution Configuration Options

        JTextField mutateBox = new JTextField("1.0");
        buttons.add(new JLabel("M Rate:_/N"));
        buttons.add(mutateBox);

        String[] selectionOp = {"Rank", "Roulette", "Truncation"};
        JComboBox<String> selectionBox = new JComboBox<>(selectionOp);
        buttons.add(new JLabel("Selection"));
        buttons.add(selectionBox);


        String[] fitnessOp= {"Simple", "Max"};
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

        JTextField elitismBox = new JTextField("50");
        buttons.add(new JLabel ("Elitism"));
        buttons.add(elitismBox);
        
        JButton startButton = new JButton("Start Simulation");
        buttons.add(startButton);

        Timer t = new Timer(0, (e) -> {

            if(simState == SimulationState.RUNNING){
                //Run the next generation
                if(sim.runGeneration()){
                    //Update the graph
                    graph.addEntry(sim.getMaxFitness(), sim.getAverageFitness(), sim.getMinFitness());
                    System.out.println("Max: "+sim.getMaxFitness()+ " Average: "+sim.getAverageFitness()+ " Min: "+sim.getMinFitness());
                } else {
                    simState = SimulationState.STOPPED;
                    startButton.setText("Start");
                }
                graph.repaint();
            }
        });
        t.start();

        startButton.addActionListener((e) -> {
            //If this is the start of the 
        	
        	
        	
        	
            if(simState == SimulationState.STOPPED){
                graph.clearGraph();

                //Check the fitness function and selection method
                if (selectionBox.getSelectedIndex() == 0) {
                    sim.setSelectionMethod(new RankSelection());
                } else if (selectionBox.getSelectedIndex() == 1) {
                    sim.setSelectionMethod(new RouletteSelection());
                } else {
                    sim.setSelectionMethod(new TruncationSelection());
                }

                if (fitnessBox.getSelectedIndex() == 0) {
                    sim.setFitnessFunction(new SimpleFitnessFunction());
                } else {
                    sim.setFitnessFunction(new MaxConsecutiveFitness());
                }


                int generations = Integer.parseInt(generationsBox.getText());
                int genomes = Integer.parseInt(genomeBox.getText());
                double mutate_rate = Double.parseDouble(mutateBox.getText());
                int population = Integer.parseInt(populationBox.getText());
                double elitism_rate = Double.parseDouble(elitismBox.getText());

                sim.startSimulation(population, genomes, (int)(100 * Math.random()), mutate_rate, generations);
             
                simState = SimulationState.RUNNING;
                startButton.setText("Pause");

            } else if(simState == SimulationState.RUNNING){
                startButton.setText("Continue");
                simState = SimulationState.PAUSED;
            } else if(simState == SimulationState.PAUSED){
                startButton.setText("Pause");
                simState = SimulationState.RUNNING;
            }
            
            

            /*
             * ToDo: Add Method for checking box
             */
            if(crossOverCheckBox.isSelected()) {
            	
            }else {
            	
            }

          
        });

        
        frame.add(graph);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        myFrame.pack();
        myFrame.setVisible(true);

    }
}
