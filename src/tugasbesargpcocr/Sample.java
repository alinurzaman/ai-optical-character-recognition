/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasbesargpcocr;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 *
 * @author ali
 */
public class Sample extends JPanel{
    SampleData datasample;
    
    Sample(final int width, final int height){
        this.datasample = new SampleData(' ', width, height);
    }
    
    SampleData getData(){
        return this.datasample;
    }
    
    @Override
    public void paint(final Graphics g){
        if (this.datasample == null){
            return;
        }
        
        int x, y;
        final int vertical = getHeight();
        final int horizontal = getWidth();
        
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.black);
        for (y = 0; y < this.datasample.getHeight(); y++) {
            g.drawLine(0, y * vertical, getWidth(), y * vertical);
        }
        for (x = 0; x < this.datasample.getWidth(); x++) {
            g.drawLine(x * horizontal, 0, x * horizontal,
                    getHeight());
        }
        for (y = 0; y < this.datasample.getHeight(); y++) {
            for (x = 0; x < this.datasample.getWidth(); x++) {
                if (this.datasample.getData(x, y)) {
                    g.fillRect(x * horizontal, y * vertical, horizontal, vertical);
                }
            }
        }
       g.setColor(Color.black);
       g.drawRect(0, 0, getWidth()-1, getHeight()-1);
       
    }
    
    void setData(final SampleData datasample){
        this.datasample = datasample;
    }
}
