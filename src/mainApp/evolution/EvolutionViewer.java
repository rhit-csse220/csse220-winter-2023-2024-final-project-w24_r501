package mainApp.evolution;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainApp.fitnessfunctions.MaxConsecutiveFitness;
import mainApp.fitnessfunctions.SimpleFitnessFunction;
import mainApp.selectionmethods.RankSelection;
import mainApp.selectionmethods.RouletteSelection;
import mainApp.selectionmethods.TruncationSelection;

public class EvolutionViewer {
    public EvolutionSimulator sim = new EvolutionSimulator();

    public EvolutionViewer() {
    }
    
    public void runViewer() {
        JFrame frame = new JFrame("Evolution Viewer");
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttons = new JPanel();
        GraphComponent graph = new GraphComponent(sim);
        graph.frame = frame;

        

        JLabel mutateLabel = new JLabel("M Rate:_/N");
        JTextField mutateBox = new JTextField("1.0");

        String[] selectionOp = {"Rank", "Roulette", "Truncation"};
        JLabel selectionLabel = new JLabel("Selection");
        JComboBox selectionBox = new JComboBox<>(selectionOp);

        String[] fitnessOp= {"Simple", "Max"};
        JComboBox fitnessBox = new JComboBox<>(fitnessOp);
        JLabel fitnessLabel = new JLabel("Fitness");

        JLabel generationsLabel = new JLabel("Generations");
        JTextField generationsBox = new JTextField("100");

        JLabel populationLabel = new JLabel("Population");
        JTextField populationBox = new JTextField("100");

        JLabel genomeLabel = new JLabel("Genome Length");
        JTextField genomeBox = new JTextField("100");

        JButton startButton = new JButton("Start Simulation");

        ActionListener painter = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sim.tryRunGeneration();
                frame.repaint();
            }
        };
        Timer t = new Timer(0, painter);
        t.start();

        startButton.addActionListener((e) -> {
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
            double mutate_rate = Double.parseDouble(mutateBox.getText()) / genomes;
            int population = Integer.parseInt(populationBox.getText());

            sim.startSimulation(population, genomes, (int)(100 * Math.random()), mutate_rate, generations);

            
        });

        buttons.add(startButton);
        buttons.add(mutateLabel);
        buttons.add(mutateBox);
        buttons.add(selectionLabel);
        buttons.add(fitnessBox);
        buttons.add(selectionLabel);
        buttons.add(selectionBox);
        buttons.add(fitnessBox);
        buttons.add(fitnessLabel);
        buttons.add(populationLabel);
        buttons.add(populationBox);
        buttons.add(generationsLabel);
        buttons.add(generationsBox);
        buttons.add(genomeLabel);
        buttons.add(genomeBox);

        frame.add(graph);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);



    }
}
