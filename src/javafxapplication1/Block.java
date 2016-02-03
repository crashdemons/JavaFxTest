/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.scene.image.Image;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class Block extends Sprite{
    public Block(Image img, Drawing di,int x, int y){
        super(img,di,x,y);
        initBlock();
    }
    public Block(Resources.id iid, Drawing di,int x, int y){
        super(iid,di,x,y);
        initBlock();
    }
    private void initBlock(){ hasGravity=true; }
}
