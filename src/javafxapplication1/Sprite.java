/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import animation.Animation;
import animation.AnmExplode;
import animation.AnmFalling;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafxapplication1.Resources.id;
/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class Sprite extends ImageView{
    public Drawing di;
    public boolean visible;
    public Animation animation;
    public int cell;
    public boolean deathfall;
    public boolean dead;
    public boolean hasGravity;
    
    public void kill(){
        disappear();
        dead=true;
    }
    public void explode(int fuse){
        deathfall=false;
        switchAnimation(new AnmExplode(fuse));
    }
    public void killAnimated(){
        deathfall=true;
        switchAnimation(new AnmFalling());
    }
    
    
    
    public void tick(long now){
        if(animation!=null) animation.tick(this,now);
    }
    public Animation.anm_id getAnimationType(){
        if(animation==null) return Animation.anm_id.NONE;
        return animation.id;
    }
    public void terminateAnimation(){
        if(animation!=null && animation.active) animation.finish(this);
        animation=null;
    }
    public void switchAnimation(Animation anm){
        terminateAnimation();
        animation=anm;
    }
    
    
    public void disappear(){
        if(!visible) return;
        di.group.getChildren().remove(this);
        visible=false;
    }
    public void appear(){
        if(visible) return;
        di.group.getChildren().add(this);
        visible=true;
    }
    
    
    public double getW(){//TODO: support resizing (but getFitWidth returns 0 in some cases)
        return this.getImage().getWidth();
    }
    public double getH(){//TODO: support resizing (but getFitWidth returns 0 in some cases)
        return this.getImage().getHeight();
    }
    public double getCenterX(){
        return getLayoutX()+getW()/2.0d;
    }
    public double getCenterY(){
        return getLayoutY()+getH()/2.0d;
    }
    public void setCenterX(double x){
        setLayoutX(x-getW()/2.0d);
    }
    public void setCenterY(double y){
        setLayoutY(y-getH()/2.0d);
    }
    public void setCenter(double x, double y){
        setCenterX(x);
        setCenterY(y);
    }
    public void setCenter(Sprite sp){
       setCenter(sp.getCenterX(),sp.getCenterY());
    }
    public void setCorner(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
    }
    
    
    
    public Sprite(Image img, Drawing di,int x, int y){
        super(img);
        init(di,x,y);
    }
    public Sprite(id iid, Drawing di,int x, int y){
        super(Resources.images.get(iid));
        init(di,x,y);
    }
    private void init(Drawing di,int x, int y){
        this.di=di;
        setLayoutX(x);
        setLayoutY(y);
        visible=true;
        animation=null;
        cell=-1;
        deathfall=false;
        dead=false;
        hasGravity=false;
    }
}
