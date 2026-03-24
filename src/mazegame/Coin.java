/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mazegame;

/**
 *
 * @author Admin
 */
public class Coin {
    private final int row;
    private final int col;
    private boolean collected;
    public Coin(int row,int col){
        this.row=row;
        this.col=col;
        this.collected=false;
    }
    public int getRow(){
        return row;
        
    }
    public int getCol(){
        return col;
    }
    public boolean isCollected(){
        return collected;
    }
    public void collect(){
        this.collected=true;
    }
}
