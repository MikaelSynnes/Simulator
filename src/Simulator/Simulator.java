package Simulator;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator {

    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;

    private static final double HUNTER_CREATION_PROBABILITY = 0.002;
    
    private static final double GRASS_CREATION_PROBABILITY=1;

    private List<Grass> grassArray;

    // List of animals in the field.
    private List<Animal> animals;
    // The current state of the field.
    private Field field;
    private Field grassField;
    // The current step of the simulation.
    private int step;
    private int incrementGrass;
    private int incrementNow;
    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<Animal>();
        field = new Field(depth, width);
        grassField = new Field(depth, width);
        grassArray = new ArrayList<Grass>();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Wolf.class, Color.ORANGE);
        view.setColor(Sheep.class, Color.BLUE);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Jeger.class, Color.BLACK);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        incrementGrass++;
        incrementNow = incrementGrass % 6;

        if (incrementNow == 0) {
            for (Grass g : grassArray) {
                g.incrementGrass();
            }
        }

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<Animal>();
        // Let all rabbits act.
        for (Iterator<Animal> it = animals.iterator(); it.hasNext();) {
            Animal animal = it.next();

            animal.act(newAnimals);
            if (!animal.isAlive()) {
                it.remove();

            }
          
        }
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);

        view.showStatus(step, field);

    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        animals.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Wolf wolf = new Wolf(true, field, location);
                    animals.add(wolf);
                } else if (rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Sheep rabbit = new Sheep(true, field, location);
                    animals.add(rabbit);
                } else if (rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Jeger jeger = new Jeger(true, field, location);
                    animals.add(jeger);
                } else if (rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location loc = new Location(row, col);
                    Grass grass = new Grass(grassField, loc);
                    grassArray.add(grass);
                }
                // else leave the location empty.
            }
        }
    }

}
