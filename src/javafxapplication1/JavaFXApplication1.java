/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage; 


/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class JavaFXApplication1 extends Application {
    Drawing drawing;
    Events events;
    
    @Override
    public void start(Stage stage) {
        drawing=new Drawing(stage);
        events=new Events(drawing);
        drawing.setup("Test", 512,512);
        stage.setX(0);
        stage.setY(1);
        drawing.start();
        drawing.scene.setOnMouseClicked(events);
        drawing.scene.setOnKeyPressed(events);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
