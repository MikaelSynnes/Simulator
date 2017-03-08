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
public class DeadAnimal {
    private int step;
    private int sheep;
    private int wolf;
    private int dSheep;
    private int dWolf;
    
    public DeadAnimal(int step, int sheep, int wolf, int dSheep, int dWolf){
        this.step = step;
        this.sheep = sheep;
        this.wolf = wolf;
        this.dSheep = dSheep;
        this.dWolf = dWolf;
    }
    
    public int getStep(){
        return step;
    }
    
    public int getSheep(){
        return sheep;
    }
    
    public int getWolf(){
        return wolf;
    }
    
    public int getDeadSheep(){
        return dSheep;
    }
    
    public int getDeadWolf(){
        return dWolf;
    }
    
}