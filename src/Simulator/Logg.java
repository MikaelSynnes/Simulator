/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

/**
 *
 * @author Kristian
 */
public class Logg {
    private int step;
    private Animal animal;
    
    public Logg(int step, Animal animal){
        this.step = step;
        this.animal = animal;
    }
    
    public int getStep(){
        return step;
    }
    
    public int getAge(){
        return animal.returnAge();
    }
    
    public Animal getAnimal(){
        return animal;
    }
    
}