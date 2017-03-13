/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 *
 * @author Kristian
 */
public class Header {
    private int x;
    private String title;


    public Header(int x, String title ) {
        this.x = x;
        this.title = title;

        
    }
    
        public Label createHeader(){
        Label header = new Label(title);
        header.setLayoutX(x);
        header.setFont(Font.font("",FontWeight.NORMAL,20));
        
        return header;
    }
}
