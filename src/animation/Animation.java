/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import javafxapplication1.Sprite;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public abstract class Animation {
    public int lifespan;
    public int interval;
    public boolean active;
    private int life;
    public enum anm_id { NONE, FALLING, COUNT};
    public anm_id id=anm_id.NONE;
    Animation(){
        active=true;
        lifespan=-1;
        interval=1;
        life=0;
    }
    public void restart(){
        life=0;
        active=true;
    }
    public void tick(Sprite sp,long now){
        if(!active) return;
        if(life>lifespan && lifespan>=0) { finish(sp); return; }
        
        if(life==0) begin(sp);
        else if(life%interval==0) animate(sp,now);
        life++;
    }
    protected void begin(Sprite sp) {}
    public void finish(Sprite sp){active=false;}
    protected abstract void animate(Sprite sp,long now);
}
