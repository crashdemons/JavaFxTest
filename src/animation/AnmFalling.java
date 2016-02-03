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
public class AnmFalling extends Animation{
    private double velocity=0.0;// m/s   (pixels/second)
    private final double terminal=18.0;//max velocity
    
    public AnmFalling(){
        super();
        id=anm_id.FALLING;
    }
    
    @Override
    protected void animate(Sprite sp, long now){
        double y=sp.getLayoutY();
        sp.setOpacity(calcMotionBlur());
        y+=velocity;
        sp.setLayoutY(y);
        velocity+=(9.81/60.0);// delta v = a*t = (9.81 m/s2) * (1/60 s)
        if(velocity>terminal) velocity=terminal;
    }
    @Override
    protected void begin(Sprite sp){
        sp.setOpacity(1.0);
    }
    @Override
    public void finish(Sprite sp){
        sp.setOpacity(1.0);
        super.finish(sp);
    }
    private double calcMotionBlur(){
        double max=0.75;
        double p=velocity/terminal;
        return 1.0 - p*max;
    }
}
