/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tugasbesargpcocr;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.PixelGrabber;

import javax.swing.JPanel;
/**
 *
 * @author ali
 */
public class DrawArea extends JPanel{
    protected Image img;
    protected Graphics graph;
    protected int Xn = -1;
    protected int Yn = -1;
    protected Sample sample;
    protected int Left;
    protected int Right;
    protected int Top;
    protected int Bottom;
    protected double ratioX;
    protected double ratioY;
    protected int[] pixelMap;
    
    DrawArea(){
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.COMPONENT_EVENT_MASK);
    }
    
    public void clear(){
        this.graph.setColor(Color.white);
        this.graph.fillRect(0, 0, getWidth(), getHeight());
        this.Left = 0;
        this.Right = 0;
        this.Top = 0;
        this.Bottom = 0;
        repaint();
    }
    
    public void downSample(){
        final int lebar = this.img.getWidth(this);
        final int tinggi = this.img.getHeight(this);
        final PixelGrabber grabber = new PixelGrabber(this.img, 0, 0, lebar, tinggi, true);
        try {
            grabber.grabPixels();
            this.pixelMap = (int[]) grabber.getPixels();
            findBounds(lebar, tinggi);
            
            final SampleData datasample = this.sample.getData();
            this.ratioX = (double) (Right - Left) / (double) datasample.getWidth();
            this.ratioY = (double) (Bottom - Top) / (double) datasample.getHeight();
            
            for (int y = 0; y < datasample.getHeight(); y++) {
                for (int x = 0; x < datasample.getWidth(); x++) {
                    if (downSampleRegion(x, y)) {
                        datasample.setData(x, y, true);
                    } else {
                        datasample.setData(x, y, false);
                    }
                }
            }
            
            this.sample.repaint();
            repaint();
        } catch (InterruptedException e) {
        }
    }
    
    protected boolean downSampleRegion(final int x, final int y){
        final int lebar = this.img.getWidth(this);
        final int startX = (int) (this.Left + (x * this.ratioX));
	final int startY = (int) (this.Top + (y * this.ratioY));
	final int endX = (int) (startX + this.ratioX);
	final int endY = (int) (startY + this.ratioY);

	for (int y2 = startY; y2 <= endY; y2++) {
		for (int x2 = startX; x2 <= endX; x2++) {
			final int location = x2 + (y2 * lebar);
			if (this.pixelMap[location] != -1) {
				return true;
			}
		}
	}
        return false;
    }
    
    protected void findBounds(final int lebar, final int tinggi){
        for (int y = 0; y < tinggi; y++) {
		if (!hLineClear(y)) {
			this.Top = y;
			break;
		}
	}
        
        for (int y = tinggi - 1; y >= 0; y--) {
		if (!hLineClear(y)) {
			this.Bottom = y;
			break;
		}
	}
	
	for (int x = 0; x < lebar; x++) {
		if (!vLineClear(x)) {
			this.Left = x;
			break;
		}
	}

	for (int x = lebar - 1; x >= 0; x--) {
		if (!vLineClear(x)) {
			this.Right = x;
			break;
		}
	}
    }
    
    public Sample getSample(){
        return this.sample;
    }
    
    public void setSample(final Sample s) {
	this.sample = s;
    }
    
    protected boolean hLineClear(final int y) {
	final int horizontal = this.img.getWidth(this);
	for (int i = 0; i < horizontal; i++) {
		if (this.pixelMap[(y * horizontal) + i] != -1) {
			return false;
		}
	}
	return true;
    }
    
    protected boolean vLineClear(final int x) {
	final int horizontal = this.img.getWidth(this);
	final int vertical = this.img.getHeight(this);
	for (int i = 0; i < vertical; i++) {
		if (this.pixelMap[(i * horizontal) + x] != -1) {
			return false;
		}
	}
	return true;
    }
    
    protected void initImage(){
        this.img = createImage(getWidth(), getHeight());
        this.graph = this.img.getGraphics();
        this.graph.setColor(Color.white);
        this.graph.fillRect(0, 0, getWidth(), getHeight());
    }
    
    @Override
    public void paint(final Graphics g){
        if (this.img == null){
            initImage();
        }
        g.drawImage(this.img, 0, 0, this);
        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.green);
        g.drawRect(Left, Top, Right - Left, Bottom - Top);
    }
    
    @Override
    protected void processMouseEvent(final MouseEvent e){
        if (e.getID() != MouseEvent.MOUSE_PRESSED) {
		return;
	}
	this.Xn = e.getX();
	this.Yn = e.getY();
    }
    
    @Override
    protected void processMouseMotionEvent(final MouseEvent e){
        if (e.getID() != MouseEvent.MOUSE_DRAGGED) {
		return;
	}
	this.graph.setColor(Color.black);
	this.graph.drawLine(this.Xn, this.Yn, e.getX(), e.getY());
	getGraphics().drawImage(this.img, 0, 0, this);
	this.Xn = e.getX();
	this.Yn = e.getY();
    }
    
}
