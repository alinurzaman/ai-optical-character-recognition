/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasbesargpcocr;

/**
 *
 * @author ali
 */
public class SampleData implements Comparable<SampleData>, Cloneable{
    protected boolean grid[][];
    protected char letter;
    
    public SampleData(final char letter, final int width, final int height){
        this.grid = new boolean[width][height];
        this.letter = letter;
    }
    
    public void clear(){
        for(int x = 0; x < this.grid.length; x++){
            for(int y = 0; y < this.grid[0].length; y++){
                this.grid[x][y] = false;
            }
        }
    }
    
    @Override
    public Object clone(){
        final SampleData obj = new SampleData(this.letter, getWidth(), getHeight());
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                obj.setData(x, y, getData(x, y));
            }
        }
        return obj;
    }
    
    public int compareTo(final SampleData o) {
        final SampleData obj = o;
        if (this.getLetter() > obj.getLetter()) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public boolean getData(final int x, final int y){
        return this.grid[x][y];
    }
    
    public int getHeight(){
        return this.grid[0].length;
    }
    
    public char getLetter(){
        return this.letter;
    }
    
    public int getWidth(){
        return this.grid.length;
    }
    
    public void setData(final int x, final int y, final boolean v){
        this.grid[x][y] = v;
    }
    
    public void setLetter(final char letter){
        this.letter = letter;
    }
    
    @Override
    public String toString(){
        return "" + this.letter;
    }
}
