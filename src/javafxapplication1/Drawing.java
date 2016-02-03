/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafxapplication1.Resources.id;
//import javafxapplication1.Resources.*;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class Drawing extends AnimationTimer{
    public Stage stage;
    public Group group;
    public Scene scene;
    public ArrayList<Sprite> sprites;
    public Grid grid;
    
    
    private boolean firstTick=true;
    private int WIDTH;
    private int HEIGHT;
    
    
    public Drawing(Stage stage){
        this.stage=stage;
        group = new Group();
        scene = new Scene(group);
        sprites=new ArrayList<Sprite>();
    }
    
    
    
    public void setup(String title, int width, int height){
        WIDTH=width;
        HEIGHT=height;
        
        int cell_size=16;
        grid=new Grid(group,cell_size, width/cell_size + 1000, height/cell_size);
        Resources.loadAllImages();
        loadInitialObjects();
        
        //group.setLay
        //group.set
        //group.setLayoutY(-100);
        
        
        scene.setFill(Color.BLACK);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.setScene(scene); 
        stage.setResizable(false);
        stage.setWidth(width+50);
        stage.setHeight(width+50);
        //stage.setMinWidth(width);
        //stage.setMinHeight(height);
        stage.sizeToScene();
        stage.show();
        
        
    }
    
    
    private void loadInitialObjects(){
        //Sprite sp = createSprite(id.TEST,0,0);
    }
    
    public boolean contains(Sprite sp){
        double x = sp.getLayoutX();
        double y = sp.getLayoutY();
        //return x>-100 && x<200 && y>-100 && y<200;
        return x>-100 && x<(WIDTH+100) && y>-100 && y<(HEIGHT+100);
    }
    
    
    public Sprite createSprite(id iid,int x, int y){
        Sprite sp=new Sprite(iid,this,x,y);
        sprites.add(sp);
        sp.di=this;
        sp.setLayoutX(x);
        sp.setLayoutY(y);
        group.getChildren().add((ImageView)sp);
        return sp;
    }
    public Sprite createBlock(id iid,int x, int y){
        Block sp=new Block(iid,this,x,y);
        sprites.add(sp);
        sp.di=this;
        sp.setLayoutX(x);
        sp.setLayoutY(y);
        group.getChildren().add((ImageView)sp);
        return sp;
    }
    public void killSprite(Sprite sp){
        sp.terminateAnimation();//finish+disable animations
        sp.di=null;
        grid.unsnap(sp);//remove from grid array
        sprites.remove(sp);//remove reference from sprite ArrayList
        group.getChildren().remove(sp);//remove from JavaFX group
        sp=null;
    }
    public void auditSprites(){
        boolean killed;
        do{
            killed=false;
            for(Sprite sp : sprites){
                if(sp.dead || !contains(sp)){
                    killSprite(sp); 
                    killed=true;
                    break; 
                }
            }
        }while(killed==true);//kill invalid sprites off until the loop completes successfully.
    }
    
    
    
    @Override
    public void handle(long now) {//changes to the scene every tick
        auditSprites();
        //System.gc();
        //int x=1;
        for(int i=0;i<sprites.size();i++){
        //for(Sprite sp : sprites){
            Sprite sp=sprites.get(i);
            sp.tick(now);
            grid.tickGravity(sp);
        }
        
        
        
        if(firstTick) firstTick=false;
    }
}
