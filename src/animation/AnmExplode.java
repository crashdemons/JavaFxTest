/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import animation.Animation.anm_id;
import javafxapplication1.Resources;
import javafxapplication1.Sprite;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class AnmExplode extends Animation{
    boolean toggle=true;
    Sprite glow=null;
    public AnmExplode(int fuse){
        super();
        if(fuse<10) fuse=10;
        id=anm_id.FALLING;
        interval=5;
        lifespan=fuse;
    }
    
    @Override
    protected void animate(Sprite sp, long now){
        if(glow!=null){
            if(toggle) glow.appear();
            else       glow.disappear();
        }
        
        toggle=!toggle;
    }
    @Override
    protected void begin(Sprite sp){
        if(sp.di!=null){
            glow=sp.di.createSprite(Resources.id.GLOW,0,0);
            glow.setCenter(sp);
            glow.setOpacity(0.8);
        }
    }
    @Override
    public void finish(Sprite sp){
        sp.disappear();
        if(sp.di!=null){
            int nDebris = Resources.random.nextInt(2)+2;
            for(int i=0;i<nDebris;i++){
                    Sprite sd=sp.di.createSprite(Resources.id.RUBBLE,0,0);
                    sd.setCenter(sp);
                    sd.switchAnimation(new AnmDeathfall());
            }
        }
        
        
        super.finish(sp);
        //glow.disappear();
        if(glow!=null) glow.kill();
        sp.kill();
    }
}
