/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Thomas
 */
public class Jeger extends Animal {

    private static final double TREFFSIKKERHET = 0.2;
    private Location location;
    private int age;
    private static final int MAX_AGE = 500;
    private boolean alive;
    private final double EMERGE = 0.1;
    private Field field;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private int foodLevel;

    public Jeger(boolean randomAge, Field field, Location location) {
        super(field, location);
        this.location = location;
        this.field = field;
        age = 0;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    private void incrementAge() {
        age++;
        if (age > MAX_AGE) {
            setDead();
        }
    }

    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Wolf) {
                Wolf wolf = (Wolf) animal;
                if (wolf.isAlive()) {
                    wolf.setDead();
                    return where;
                }
            }
        }
        return null;
    }

    /*private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
     */
    public void act(List<Animal> newFoxes) {
        incrementAge();
        //incrementHunger();
        if (isAlive()) {
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

}
