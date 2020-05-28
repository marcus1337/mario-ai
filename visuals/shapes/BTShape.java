package visuals.shapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import agents.BT.BT.Node;
import visuals.TextInBox;

public abstract class BTShape extends Path2D.Double {
	
	protected Color BOX_COLOR = Color.LIGHT_GRAY;
	protected Color BORDER_COLOR = Color.DARK_GRAY;
	protected Color TEXT_COLOR = Color.black;
	
	public String text = "Hello world!";
	public AttributedString trig = null;
	public static int ARC_SIZE = 5;
	public Font font = null;
	
	protected void setPath(double width, double height){
		throw new UnsupportedOperationException();
	}
	
	public BTShape() {
	}
	public BTShape(int x, int y) {
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
	
	protected void paintBoxBackground(Graphics2D g, Node box) {
		g.setColor(BOX_COLOR);
		g.fillRoundRect(x, y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);
	}

	protected void paintBoxMargin(Graphics2D g, Node box) {
		g.setColor(BORDER_COLOR);
		g.drawRoundRect(x, y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);
	}

	protected void paintText(Graphics2D g, Node box) {
		if(box.text.isEmpty())
			return;

		FontMetrics metrics = g.getFontMetrics(font);
	    int txtX =  x + (box.width - metrics.stringWidth(text)) / 2;
	    int txtY =  y + ((box.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    Font oldFont = g.getFont();
	    g.setFont(font); 
	    g.setColor(TEXT_COLOR);
	    
	    if(text.length() == 1)
	    	g.drawString(text, txtX, txtY);
	    else if(text.length() == 2){
            g.drawString(trig.getIterator(), txtX + font.getSize()/3, txtY);
	    }else{
	    	g.drawString(text, txtX , txtY);
	    }
	    
	    g.setFont(oldFont);
	}
	
	public abstract void paint(Graphics2D g, Node box);
}
