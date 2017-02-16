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

    private Location loc;
    private int GRASS_MAX_VALUE;
    private int value = 5;
    private Field field;

    public Grass(Field field, Location loc) {

        this.loc = loc;
        this.field = field;

    }

    public void eatGrass() {
        if (value >0) {
            value--;
        }
    }
    public Location returnLocation(){
        return loc;
    }

    public void incrementGrass() {
            if (value <GRASS_MAX_VALUE) value++;
    }

    private int grassValue() {
        return value;

    }
}
