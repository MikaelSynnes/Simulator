package Simulator;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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
    private static final double FOX_CREATION_PROBABILITY = 0.04;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;

    private static final double HUNTER_CREATION_PROBABILITY = 0.002;

    private static final double GRASS_CREATION_PROBABILITY = 1;

    private List<Grass> grassArray;

    // List of animals in the field.
    private List<Animal> animals;
    private HashMap<Integer,Animal> deadAnimals;
    private List<Logg> loggfil;
    private List<Logg> stepAnimal;
    // The current state of the field.
    private Field field;
    private Field grassField;
    // The current step of the simulation.
    private int step;
    private int incrementGrass;
    private int incrementNow;
    int deadHunter;
    int averageDeathHunter;
    int deadSheep;
    int averageDeathSheep;
    int deadWolf;
    int averageDeathWolf;
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
        deadAnimals = new HashMap<Integer,Animal>();
        field = new Field(depth, width);
        grassField = new Field(depth, width);
        grassArray = new ArrayList<Grass>();
        loggfil = new ArrayList<Logg>();
        stepAnimal = new ArrayList<Logg>();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Wolf.class, Color.ORANGE);
        view.setColor(Sheep.class, Color.BLUE);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Jeger.class, Color.RED);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(1000);
        deadAnimals();

    }

    public void logger(){
        try{
        PrintWriter wWriter = new PrintWriter("Wolf.txt", "UTF-8");
        PrintWriter sWriter = new PrintWriter("Sheep.txt", "UTF-8");
        wWriter.println("Step\tAge\tAnimal");
        sWriter.println("Step\tAge\tAnimal");
         for (Logg l : loggfil){
                if(l.getAnimal() instanceof Wolf){
                    wWriter.println(l.getStep()+"\t"+l.getAge()+"\t"+"Wolf");  
                }
                if(l.getAnimal() instanceof Sheep){
                    sWriter.println(l.getStep()+"\t"+l.getAge()+"\t"+"Sheep");
                }
            }
        wWriter.close();
        sWriter.close();
        } catch (IOException e) {
       // do something
        }
       
    }
        
    public void deadAnimals(){
        List<DeadAnimal> dAnimals = new ArrayList<>();
        int deadSheep1 = 0;
        int deadWolf1 = 0;
        int sheep1=0; 
        int wolf1 = 0;

        for(int i=1; i<=4000;i++){
            
            for(Logg l : loggfil){
                if(l.getStep() == i){
                    if(l.getAnimal() instanceof Wolf){
                        deadWolf1++;
                    }
                    if(l.getAnimal() instanceof Sheep){
                        deadSheep1++;
                    }
                }       
            }
            for(Logg l: stepAnimal){
                if(l.getStep() == i){
                    if(l.getAnimal() instanceof Wolf){
                        wolf1++;
                    }
                    if(l.getAnimal() instanceof Sheep){
                        sheep1++;
                    }
                }
            }
            
            dAnimals.add(new DeadAnimal(i,sheep1,wolf1,deadSheep1,deadWolf1));

            deadSheep1 = 0;
            deadWolf1 = 0;
            wolf1 = 0;
            sheep1=0;
            try{
                PrintWriter writer = new PrintWriter("DeadPerStep.txt", "UTF-8");
                writer.println("Step\tSheep\tWolf\tdSheep\tdWolf");
                for(DeadAnimal d: dAnimals){
                    writer.println(d.getStep()+"\t"+d.getSheep()+"\t"+d.getWolf()+"\t"+d.getDeadSheep()+"\t"+d.getDeadWolf());
                }
                writer.close();
            } catch (IOException e) {
           // do something
            
            }
        
        }
        
         
    }
        
    public void logg() {

        for (Animal animal : deadAnimals.values()) {

            System.out.println(animal.returnAge());
            if (animal instanceof Jeger) {

                deadHunter++;
                averageDeathHunter = averageDeathHunter + animal.returnAge();
            }
            if (animal instanceof Wolf) {
                deadWolf++;
                averageDeathWolf = averageDeathWolf + animal.returnAge();
            }
            if (animal instanceof Sheep) {
                deadSheep++;
                averageDeathSheep = averageDeathSheep + animal.returnAge();
            }

        }

        int h = 1;// (averageDeathHunter / deadHunter);
        int s = (averageDeathSheep / deadSheep);
        int w = (averageDeathWolf / deadWolf);
        System.out.println(averageDeathWolf);
        System.out.println(w + s);
        System.out.println("Jeger: " + deadHunter + " gjennomsnitt: " + h + "  Ulv " + deadWolf + " gjennomsnitt: " + w + " Sau " + deadSheep + " gjennomsnitt: " + s);
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
        for(Animal a : animals){
            if(a instanceof Sheep){
                stepAnimal.add(new Logg(step,a));
            }
            if(a instanceof Wolf){
                stepAnimal.add(new Logg(step,a));
            }
        }
        incrementGrass++;
        incrementNow = incrementGrass % 6;

        if (incrementNow == 0) {
            for (Grass g : grassArray) {
                g.incrementGrass();
            }
        }

        for (Animal animal : deadAnimals.values()) {
            if (animal.isAlive() == true) {
                System.out.println("Error");
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
                //System.out.println(animal.returnAge());
                deadAnimals.put(step,animal);
                Logg dead = new Logg(step, animal);
                loggfil.add(dead);
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
