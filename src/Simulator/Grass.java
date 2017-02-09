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
    private int value;
    
    
    
    public Grass(int value, Location loc){
        this.value=value;
        this.loc=loc;
    
    }
    
    
    public void eatGrass(){
        value--;
    }
    
    
    private int grassValue(){
        return value;
        
    }
}
