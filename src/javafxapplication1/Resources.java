/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class Resources {
    public static HashMap<id, Image> images = new HashMap<id, Image>();
    public static enum id{ TEST,RUBBLE,GLOW,           COUNT};
    public static String names[] = new String[]{"TEST","RUBBLE","GLOW","INVALID"};
    public static Random random=new Random();
    

    
    public static void loadAllImages(){ for(String name : names) loadImage(name); }
    public static void loadImage(String name){
        id iid=getIdByName(name);
        if(images.containsKey(iid)) return;
        //System.out.println("image/"+name+".png");
        InputStream is = JavaFXApplication1.class.getResourceAsStream("/image/"+name+".png");
        Image img = new Image(is);
        //Image img = new Image("image/"+name+".png");
        images.put(iid, img);
    }
    
    public static Image getImageByName(String name){ return images.get(getIdByName(name)); }
    public static ImageView getIV(id iid){ return new ImageView(images.get(iid)); }
    public static ImageView getIVbyName(String name){ return new ImageView(images.get(getIdByName(name))); }
    
   
    public static String getNameById(id iid){
        int i=iid.ordinal();
        if(i<0 || i>=id.COUNT.ordinal()) return names[id.COUNT.ordinal()];
        return names[iid.ordinal()];
    }
    public static id getIdByName(String name){
        int i=id.COUNT.ordinal();
        for(int j=0;j<names.length;j++) if(names[j].equals(name)){ i=j; break; }
        return id.values()[i];
    }
    
}
