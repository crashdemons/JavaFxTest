/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import animation.AnmFalling;
import animation.Animation.anm_id;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author crashdemons <crashdemons -at- github.com>
 */
public class Grid {
    Sprite[] cells;
    Line[] lines;
    private        final int CELL_SIZE;
    private        final int GRID_WIDTH;
    private        final int GRID_HEIGHT;
    private        final int CELL_COUNT;
    private        final int GRID_WIDTH_PX;
    private        final int GRID_HEIGHT_PX;
    Grid(Group group,int cellsize,int gridwidth,int gridheight){
        CELL_SIZE=cellsize;
        GRID_WIDTH=gridwidth;
        GRID_HEIGHT=gridheight;
        GRID_WIDTH_PX=gridwidth*cellsize;
        GRID_HEIGHT_PX=gridheight*cellsize;
        CELL_COUNT=GRID_WIDTH*GRID_HEIGHT;
        cells=new Sprite[CELL_COUNT];
        for(int i=0;i<CELL_COUNT;i++) cells[i]=null;//not sure if necessary
        
        int width=GRID_WIDTH*CELL_SIZE;
        int height=GRID_HEIGHT*CELL_SIZE;
        for(int x=0;x<=width;x+=CELL_SIZE){//vertical lines
            Line line = new Line(x, 0, x, height);   
            //line.setStrokeWidth(20); 
            line.setStroke(Color.web("00FF00"));
            group.getChildren().add(line);
        }
        for(int y=0;y<=height;y+=CELL_SIZE){//horizontal lines
            Line line = new Line(0, y, width, y);   
            //line.setStrokeWidth(20); 
            line.setStroke(Color.web("00FF00")); 
            group.getChildren().add(line);
        }
    }
    
    
    
    
    
    
    
    public int[] CelltoRC(int cell){
        return new int[]{ cell/GRID_WIDTH, cell%GRID_WIDTH  };
    }
    public int RCtoCell(int row, int col){
        return row*GRID_WIDTH + col;
    }
    public int[] XYtoRC(int x, int y){
        return new int[]{y/CELL_SIZE, x/CELL_SIZE};
    }
    public int[] RCtoXY(int row, int col){
        return new int[]{col*CELL_SIZE, row*CELL_SIZE};
    }
    public int XYtoCell(int x, int y){
        int row=y/CELL_SIZE;
        int col=x/CELL_SIZE;
        int cell = row*GRID_WIDTH + col;
        return cell;
    }
    public int[] CelltoXY(int cell){
        return new int[]{
            (cell%GRID_WIDTH)*CELL_SIZE,
            (cell/GRID_WIDTH)*CELL_SIZE
        };
    }
    public int[] AlignXY(int x,int y){
        return new int[]{
            ((int)(x/CELL_SIZE))*CELL_SIZE,
            ((int)(y/CELL_SIZE))*CELL_SIZE
        };
    }
    public boolean validateCell(int cell){
        return cell>=0 && cell<CELL_COUNT;
    }
    
    public int AdjustCellByRC(int cell, int dr, int dc){
        int[] rc=CelltoRC(cell);
        rc[0]+=dr;
        rc[1]+=dc;
        if(rc[0]<0 || rc[0]>=GRID_HEIGHT) return -1;
        if(rc[1]<0 || rc[1]>=GRID_WIDTH ) return -1;
        return RCtoCell(rc[0],rc[1]);
    }
    
    
    public boolean isVacant(int cell){ 
        if(cell<0 || cell>=CELL_COUNT) return false;
        return cells[cell]==null;
    }
    public boolean isRelativeVacant(int cell1, int dr, int dc){
        int cell=AdjustCellByRC(cell1,dr,dc);
        return isVacant(cell);
    }
    
    public boolean canFall(Sprite sp){
        int x=(int)sp.getLayoutX();
        int y=(int)sp.getLayoutY();
        int cell=XYtoCell(x,y);
        return isAboveFloor(sp) &&  isRelativeVacant(cell,1,0);//is the cell above the bottom of the plane and the next lower cell vacant?
    }
    
    public boolean isAboveFloor(Sprite sp){
        int y=(int)sp.getLayoutY();
        //System.out.println(y + " < " + ( (GRID_HEIGHT-1)*CELL_SIZE )  );
        return y< ( (GRID_HEIGHT-1)*CELL_SIZE );
    }
    public int getCurrentCell(Sprite sp){
        if(sp.cell!=-1) return sp.cell;
        int x=(int)sp.getLayoutX();
        int y=(int)sp.getLayoutY();
        int cell=XYtoCell(x,y);
        //System.out.println("x="+x+" y="+y+" cell="+cell);
        return cell;//precision loss of X,Y to grid intervals
    }
    public boolean isSnapped(Sprite sp){ return (sp.cell!=-1); }
    public boolean contains(Sprite sp){
        double x=sp.getLayoutX();
        double y=sp.getLayoutY();
        return (x>=0 && x<GRID_WIDTH_PX) && (y>=0 && y<GRID_HEIGHT_PX);
    }
    public void unsnap(Sprite sp){
        if(sp.cell==-1) return;
        cells[sp.cell]=null;
        sp.cell=-1;
    }
    public boolean snap(Sprite sp, int cell){
        if(cell<0 || cell>=CELL_COUNT) return false;
        if(cells[cell]!=null) return false;//cell occupied
        int xy[]=CelltoXY(cell);
        sp.setLayoutX(xy[0]);
        sp.setLayoutY(xy[1]);
        cells[cell]=sp; 
        sp.cell=cell;
        return true;
    }
    public int findNextVacant(int cell){
        while(cell>=0){
            cell=AdjustCellByRC(cell,-1,0);
            if(isVacant(cell)) return cell;
        }
        return -1;
    }
    public void fallStart(Sprite sp){
        //System.out.println("FALL: "+sp);
        unsnap(sp);
        sp.switchAnimation(new AnmFalling());
    }
    public boolean fallEnd(Sprite sp){
        sp.terminateAnimation();
        int cell=getCurrentCell(sp);
        boolean snapped=snap(sp,cell);//snap in place
        if(!snapped){ //two sprites fell into the same cell during the same tick.
            cell=findNextVacant(cell);//snap to the next higher vacant cell.
            snapped=snap(sp,cell);
        }
        //System.out.println("REST: "+sp+ " success="+snapped);
        return snapped;
    }
    public void tickGravity(Sprite sp){
        if(sp.deathfall) return;//do not interrupt sprite death animation
        if(!sp.hasGravity) return;//only sprites allowing gravity can be managed by the grid.
        if(!contains(sp)) { sp.killAnimated(); return; }
        boolean isFalling=(sp.getAnimationType()==anm_id.FALLING);
        boolean willFall=canFall(sp);
        
        
        if( willFall && !isFalling){ fallStart(sp); }
        if(!willFall &&  isFalling){
            if(isSnapped(sp)) return;
            fallEnd(sp);
        }
    }
    
}
