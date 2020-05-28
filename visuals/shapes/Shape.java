package visuals.shapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Path2D;
import java.text.AttributedString;

import visuals.TextInBox;

public abstract class Shape extends Path2D.Double {
	
	protected Color BOX_COLOR = Color.orange;
	protected Color BORDER_COLOR = Color.darkGray;
	protected Color TEXT_COLOR = Color.black;
	
	protected void setPath(double width, double height){
		throw new UnsupportedOperationException();
	}
	
	public Shape() {
	}
	public Shape(int x, int y) {
		setXY(x,y);
	}

	protected int x;
	protected int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String text = "Hello world!";
	public AttributedString trig = null;

	protected void paintText(Graphics2D g, TextInBox box, Font font) {
		if(box.text.isEmpty())
			return;

		FontMetrics metrics = g.getFontMetrics(font);
	    int txtX =  x + (box.width - metrics.stringWidth(text)) / 2;
	    int txtY =  y + ((box.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    Font oldFont = g.getFont();
	    g.setFont(font); 
	    
	    if(text.length() == 1)
	    	g.drawString(text, txtX, txtY);
	    else if(text.length() == 2){
            g.drawString(trig.getIterator(), txtX + font.getSize()/3, txtY);
	    }else{
	    	g.drawString(text, txtX , txtY);
	    }
	    
	    g.setFont(oldFont);
	}
	
	public abstract void paint(Graphics2D g, TextInBox box, Font font);
}
