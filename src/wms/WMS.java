/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wms;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Mikael
 */
public class WMS extends Application {

    ArrayList<Button> buttons;

    @Override
    public void start(Stage primaryStage) {
        buttons = new ArrayList<Button>();
        
    
        // lager knapper og legger dem til i en ArrayList for å legge dem til i scena uten og å måtte kodeduplisere
        
        Button btn = createButton(100, 100,"Tekst",null); buttons.add(btn);
        Button btn2 = createButton(400, 100,"Tekst",null); buttons.add(btn2);
        Button btn3 = createButton(400, 300,"Tekst",null); buttons.add(btn3);
        Button btn4 = createButton(100, 300,"Tekst",null); buttons.add(btn4);
       
        Pane root = new Pane();

        for (Button b : buttons) {
            root.getChildren().add(b);
        }
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    
        // Lager en knapp med posisjon pixels int,int og String tekst, Og hvor knappen skal lede til null (scene). 
        
    public Button createButton(int x, int y, String title, Scene scene) {
        Button btn = new Button();

        btn.setText(title);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene);
            }
        });
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setMinSize(250,150);
        return btn;
    }

}
