/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

/**
 *
 * @author Mikael
 */
public class Grass {

    private Location location;
    private int GRASS_MAX_VALUE = 5;
    private int value = 5;
    private Field field;

    public Grass(Field field, Location loc) {
        this.location=loc;
        this.field = field;
        setLocation(loc);

    }

    public Boolean Grass() {
        if (grassValue() > 0) {
           
            return true;

        } else {
            return false;
        }
    }
    public void decreaseGrass(){
        value--;
    }

    public Location returnLocation() {
        return location;
    }

    public void incrementGrass() {
        if (grassValue() < GRASS_MAX_VALUE) {
            value++;
        }
    }

    public int grassValue() {
        return value;

    }

    void setLocation(Location newLocation) {

        location = newLocation;
        field.place(this, newLocation);
    }

    public Field getField() {
        return field;
    }
}
