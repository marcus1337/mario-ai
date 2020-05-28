package visuals.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import visuals.TextInBox;

public abstract class Shape extends Path2D.Double {
	
	protected int ARC_SIZE = 10;
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
	
	public abstract void paint(Graphics2D g, TextInBox box);
}
