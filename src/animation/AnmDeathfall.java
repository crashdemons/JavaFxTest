/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;
import javafxapplication1.Resources;
import javafxapplication1.Sprite;
/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class AnmDeathfall extends Animation{
    private double xvelocity=3.0;
    private double velocity=-3.0;// m/s   (pixels/second)
    private final double terminal=18.0;//max velocity
    
    public AnmDeathfall(){
        super();
        id=Animation.anm_id.FALLING;
        xvelocity=Resources.random.nextInt(7)-3.0;
        velocity=-(Resources.random.nextInt(7)+1.0);
    }
    
    @Override
    protected void animate(Sprite sp, long now){
        double x=sp.getLayoutX();
        double y=sp.getLayoutY();
        sp.setOpacity(calcMotionBlur());
        x+=xvelocity;
        y+=velocity;
        sp.setLayoutY(y);
        sp.setLayoutX(x);
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
