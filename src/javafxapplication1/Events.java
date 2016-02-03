/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;
import static javafx.scene.input.MouseButton.*;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class Events implements EventHandler<Event>{
    Drawing di;
    Events(Drawing di){ this.di=di; }
    @Override
    public void handle(Event e) {
         if( e.getEventType()==MOUSE_CLICKED ) onMouseClicked((MouseEvent) e);
         if( e.getEventType()==KEY_PRESSED   ) onKeyPress    ((KeyEvent)   e);
    }
    public void onKeyPress(KeyEvent e){
        if(e.getCode()==KeyCode.ESCAPE){
            System.out.println("exit");
            Platform.exit();
        }
        if(e.getCode()==KeyCode.UP){
            di.group.setLayoutY(di.group.getLayoutY()+10);
        }
        if(e.getCode()==KeyCode.DOWN){
            di.group.setLayoutY(di.group.getLayoutY()-10);
        }
        if(e.getCode()==KeyCode.LEFT){
            di.group.setLayoutX(di.group.getLayoutX()+10);
        }
        if(e.getCode()==KeyCode.RIGHT){
            di.group.setLayoutX(di.group.getLayoutX()-10);
        }
    }
    public void onMouseClicked(MouseEvent e){
        int x=(int) e.getX();
        int y=(int) e.getY();
        //int x=(int)( e.getSceneX() - di.group.getLayoutX() );
        //int y=(int)( e.getSceneY() - di.group.getLayoutY() );
        int xy[]=di.grid.AlignXY(x,y);//cell corner XY
        int cell=di.grid.XYtoCell(x, y);
        
        if(e.getButton()==PRIMARY){
            di.grid.fallStart(
                di.createBlock(Resources.id.TEST, xy[0], xy[1])
            );
        }
        if(e.getButton()==SECONDARY){
            if(!di.grid.validateCell(cell)) return;
            if(di.grid.isVacant(cell)) return;
            di.grid.cells[cell].explode(10);
        }
    }
}
